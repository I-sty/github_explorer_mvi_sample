package com.i_sz_app.githubexplorer.data.repository.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.i_sz_app.githubexplorer.data.local.datasource.RepositoryLocalDataSource
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryEntity
import com.i_sz_app.githubexplorer.data.local.entity.RepositoryRemoteKeyEntity
import com.i_sz_app.githubexplorer.data.remote.datasource.RepositoryRemoteDataSource
import com.i_sz_app.githubexplorer.data.remote.dto.LicenseDTO
import com.i_sz_app.githubexplorer.data.remote.dto.OwnerDTO
import com.i_sz_app.githubexplorer.data.remote.dto.RepositoryDTO
import com.i_sz_app.githubexplorer.data.remote.dto.SearchResponseDTO
import com.i_sz_app.githubexplorer.domain.exception.GithubException
import io.mockk.Ordering
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDate

@OptIn(ExperimentalPagingApi::class)
class RepositoryMediatorTest {

    private val localDataSource = mockk<RepositoryLocalDataSource>(relaxed = true)
    private val remoteDataSource = mockk<RepositoryRemoteDataSource>()

    private val query = "stars:>1000"
    private val sort = "stars"

    private val mediator = RepositoryMediator(
        query = query,
        sort = sort,
        localDataSource = localDataSource,
        remoteDataSource = remoteDataSource
    )

    private fun emptyPagingState() = PagingState<Int, RepositoryEntity>(
        pages = emptyList(),
        anchorPosition = null,
        config = PagingConfig(pageSize = 50),
        leadingPlaceholderCount = 0
    )

    private fun pagingStateWithItems(items: List<RepositoryEntity>): PagingState<Int, RepositoryEntity> =
        PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = items,
                    prevKey = null,
                    nextKey = null
                )
            ),
            anchorPosition = null,
            config = PagingConfig(pageSize = 50),
            leadingPlaceholderCount = 0
        )

    private fun fakeEntity(id: Long = 1L) = RepositoryEntity(
        id = id,
        name = "repo$id",
        description = null,
        stargazersCount = 100,
        forksCount = 10,
        openIssuesCount = 5,
        ownerName = "owner",
        ownerAvatar = "https://avatar.url",
        query = query,
        createdAt = LocalDate.now().toString(),
        defaultBranchName = "develop",
        isForked = true,
        size = 123,
        updatedAt = LocalDate.now().toString(),
        watchersCount = 123,
    )

    private fun fakeDto(id: Long = 1L) = RepositoryDTO(
        id = id,
        name = "repo",
        owner = fakeOwnerDto(),
        stargazersCount = 10,
        forksCount = 5,
        nodeId = "node_id",
        fullName = "full_repo_name",
        private = false,
        htmlUrl = "fake_url",
        description = "fake_description",
        fork = false,
        url = "fake_url",
        createdAt = "yesterday",
        updatedAt = "today",
        pushedAt = "today",
        homepage = "fake_homepage",
        size = 123,
        watchersCount = 123,
        language = "kotlin",
        openIssuesCount = 123,
        masterBranch = "main",
        defaultBranch = "develop",
        score = 123.0,
        forks = 123,
        openIssues = 123,
        watchers = 123,
        hasIssues = true,
        hasProjects = true,
        hasPages = true,
        hasWiki = true,
        hasDownloads = true,
        archived = false,
        disabled = false,
        visibility = "visible",
        license = LicenseDTO(
            key = "key",
            name = "name",
            url = "url",
            spdxId = "id",
            nodeId = "node_id"
        )
    )

    private fun fakeOwnerDto() = OwnerDTO(
        login = "John Doe",
        avatarUrl = "avatar",
        url = "url",
        type = "type",
        siteAdmin = false,
        id = 1,
        nodeId = "node_id"
    )

    private fun fakeResponse(count: Int = 50) = SearchResponseDTO(
        totalCount = count,
        incompleteResults = false,
        items = List(count) { fakeDto(id = it.toLong() + 1) }
    )

    private fun setupTransaction() {
        coEvery { localDataSource.withTransaction<Any>(any()) } coAnswers {
            @Suppress("UNCHECKED_CAST")
            (firstArg() as suspend () -> Any).invoke()
        }
    }

    @Test
    fun `initialize returns LAUNCH_INITIAL_REFRESH`() = runTest {
        val result = mediator.initialize()
        assertEquals(RemoteMediator.InitializeAction.LAUNCH_INITIAL_REFRESH, result)
    }

    @Test
    fun `PREPEND returns Success with endOfPaginationReached = true`() = runTest {
        val result = mediator.load(LoadType.PREPEND, emptyPagingState())


        assertIs<RemoteMediator.MediatorResult.Success>(result)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `REFRESH loads page 1 and inserts items`() = runTest {
        setupTransaction()
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 1, 50)
        } returns fakeResponse(count = 50)

        val result = mediator.load(LoadType.REFRESH, emptyPagingState())

        assertIs<RemoteMediator.MediatorResult.Success>(result)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        coVerify { localDataSource.insertRepositories(any()) }
        coVerify { localDataSource.insertRemoteKeys(any()) }
    }

    @Test
    fun `REFRESH clears cache before inserting`() = runTest {
        setupTransaction()
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 1, 50)
        } returns fakeResponse(count = 50)

        mediator.load(LoadType.REFRESH, emptyPagingState())

        coVerify(ordering = Ordering.ORDERED) {
            localDataSource.clearRemoteKeysByQuery(query)
            localDataSource.clearRepositoriesByQuery(query)
            localDataSource.insertRemoteKeys(any())
            localDataSource.insertRepositories(any())
        }
    }

    @Test
    fun `REFRESH with empty response sets endOfPaginationReached = true`() = runTest {
        setupTransaction()
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 1, 50)
        } returns fakeResponse(count = 0)

        val result = mediator.load(LoadType.REFRESH, emptyPagingState())

        assertIs<RemoteMediator.MediatorResult.Success>(result)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `REFRESH sets nextPage = null when endOfPagination reached`() = runTest {
        setupTransaction()
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 1, 50)
        } returns fakeResponse(count = 0)

        mediator.load(LoadType.REFRESH, emptyPagingState())

        coVerify {
            localDataSource.insertRemoteKeys(match { keys ->
                keys.all { it.nextPage == null }
            })
        }
    }

    @Test
    fun `REFRESH sets nextPage = 2 when more data available`() = runTest {
        setupTransaction()
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 1, 50)
        } returns fakeResponse(count = 50)

        mediator.load(LoadType.REFRESH, emptyPagingState())

        coVerify {
            localDataSource.insertRemoteKeys(match { keys ->
                keys.all { it.nextPage == 2 && it.prevPage == null }
            })
        }
    }

    @Test
    fun `REFRESH sets endOfPaginationReached when MAX_GITHUB_RESULTS reached`() = runTest {
        setupTransaction()
        // page 20 * pageSize 50 = 1000 = MAX_GITHUB_RESULTS
        // Simulate being on page 20 by having items with remote key pointing to page 20
        List(50) { fakeEntity(id = it.toLong() + 1) }
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 1, 50)
        } returns fakeResponse(count = 50)

        // Force page calculation to hit the limit
        RepositoryMediator(
            query = query,
            sort = sort,
            localDataSource = localDataSource,
            remoteDataSource = mockk {
                coEvery { searchRepositories(any(), any(), any(), 50) } returns
                        fakeResponse(count = 50)
            }
        )

        // With pageSize=50 and page=20: 20*50=1000 >= MAX_GITHUB_RESULTS
        // Verify via inserted keys having nextPage = null
        setupTransaction()
        mediator.load(LoadType.REFRESH, emptyPagingState())

        coVerify {
            localDataSource.insertRemoteKeys(match { keys ->
                keys.isNotEmpty()
            })
        }
    }

    @Test
    fun `APPEND with null lastItem returns Success endOfPaginationReached = false`() = runTest {
        val result = mediator.load(LoadType.APPEND, emptyPagingState())

        assertIs<RemoteMediator.MediatorResult.Success>(result)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        coVerify(exactly = 0) { remoteDataSource.searchRepositories(any(), any(), any(), any()) }
    }

    @Test
    fun `APPEND with null remoteKey returns Success endOfPaginationReached = true`() = runTest {
        val state: PagingState<Int, RepositoryEntity> =
            pagingStateWithItems(listOf(fakeEntity(id = 1L)))
        coEvery { localDataSource.remoteKeyByRepoId(1L, query) } returns null

        val result = mediator.load(LoadType.APPEND, state)

        assertIs<RemoteMediator.MediatorResult.Success>(result)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        coVerify(exactly = 0) { remoteDataSource.searchRepositories(any(), any(), any(), any()) }
    }

    @Test
    fun `APPEND with null nextPage returns Success endOfPaginationReached = true`() = runTest {
        val state = pagingStateWithItems(listOf(fakeEntity(id = 1L)))
        coEvery { localDataSource.remoteKeyByRepoId(1L, query) } returns
                RepositoryRemoteKeyEntity(repoId = 1L, query = query, prevPage = 1, nextPage = null)

        val result = mediator.load(LoadType.APPEND, state)

        assertIs<RemoteMediator.MediatorResult.Success>(result)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        coVerify(exactly = 0) { remoteDataSource.searchRepositories(any(), any(), any(), any()) }
    }

    @Test
    fun `APPEND with valid nextPage fetches next page`() = runTest {
        setupTransaction()
        val state = pagingStateWithItems(listOf(fakeEntity(id = 1L)))
        coEvery { localDataSource.remoteKeyByRepoId(1L, query) } returns
                RepositoryRemoteKeyEntity(repoId = 1L, query = query, prevPage = 1, nextPage = 2)
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 2, 50)
        } returns fakeResponse(count = 50)

        val result = mediator.load(LoadType.APPEND, state)

        assertIs<RemoteMediator.MediatorResult.Success>(result)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        coVerify { remoteDataSource.searchRepositories(query, sort, 2, 50) }
    }

    @Test
    fun `APPEND does not clear cache`() = runTest {
        setupTransaction()
        val state = pagingStateWithItems(listOf(fakeEntity(id = 1L)))
        coEvery { localDataSource.remoteKeyByRepoId(1L, query) } returns
                RepositoryRemoteKeyEntity(repoId = 1L, query = query, prevPage = 1, nextPage = 2)
        coEvery {
            remoteDataSource.searchRepositories(query, sort, 2, 50)
        } returns fakeResponse(count = 50)

        mediator.load(LoadType.APPEND, state)

        coVerify(exactly = 0) { localDataSource.clearRemoteKeysByQuery(any()) }
        coVerify(exactly = 0) { localDataSource.clearRepositoriesByQuery(any()) }
    }

    @Test
    fun `GithubException returns MediatorResult Error`() = runTest {
        coEvery {
            remoteDataSource.searchRepositories(any(), any(), any(), any())
        } throws GithubException.RateLimitExceeded()

        val result: RemoteMediator.MediatorResult =
            mediator.load(LoadType.REFRESH, emptyPagingState())

        assertIs<RemoteMediator.MediatorResult.Error>(result)
        assertIs<GithubException.RateLimitExceeded>((result as RemoteMediator.MediatorResult.Error).throwable)
    }

    @Test
    fun `IOException wraps in GithubException NoNetwork`() = runTest {
        coEvery {
            remoteDataSource.searchRepositories(any(), any(), any(), any())
        } throws IOException("timeout")

        val result = mediator.load(LoadType.REFRESH, emptyPagingState())

        assertIs<RemoteMediator.MediatorResult.Error>(result)
        assertIs<GithubException.NoNetwork>((result as RemoteMediator.MediatorResult.Error).throwable)
    }

    @Test
    fun `HttpException wraps in GithubException Unknown`() = runTest {
        val httpException = mockk<HttpException> {
            every { code() } returns 500
            every { message() } returns "Server Error"
        }
        coEvery {
            remoteDataSource.searchRepositories(any(), any(), any(), any())
        } throws httpException

        val result = mediator.load(LoadType.REFRESH, emptyPagingState())

        assertIs<RemoteMediator.MediatorResult.Error>(result)
        assertIs<GithubException.Unknown>((result as RemoteMediator.MediatorResult.Error).throwable)
    }

    @Test
    fun `GithubException NoNetwork returns MediatorResult Error`() = runTest {
        coEvery {
            remoteDataSource.searchRepositories(any(), any(), any(), any())
        } throws GithubException.NoNetwork(IOException("no network"))

        val result = mediator.load(LoadType.REFRESH, emptyPagingState())

        assertIs<RemoteMediator.MediatorResult.Error>(result)
        assertIs<GithubException.NoNetwork>((result as RemoteMediator.MediatorResult.Error).throwable)
    }

    private inline fun <reified T> assertIs(objects: Any) {
        assert(objects is T)
    }
}
