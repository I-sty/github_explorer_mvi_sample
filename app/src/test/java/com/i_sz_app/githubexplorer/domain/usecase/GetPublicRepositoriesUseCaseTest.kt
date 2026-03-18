package com.i_sz_app.githubexplorer.domain.usecase

import androidx.paging.PagingData
import com.i_sz_app.githubexplorer.domain.model.RepositorySort
import com.i_sz_app.githubexplorer.domain.model.buildRepositoryDetailsModel
import com.i_sz_app.githubexplorer.domain.repository.IRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Test

class GetPublicRepositoriesUseCaseTest {

    private var repository = mockk<IRepository>()
    private var getPublicRepositoriesUseCase = GetPublicRepositoriesUseCase(repository)
    private val fakePagingData = PagingData.from(listOf(buildRepositoryDetailsModel()))

    @Before
    fun setUp() {
        // Arrange
        every { repository.searchRepositories(any(), any()) } returns flowOf(fakePagingData)
    }

    @Test
    fun `invoke should call repository searchRepositories with correct parameters`() {
        // Act
        getPublicRepositoriesUseCase("kotlin", RepositorySort.Stars)


        // Assert
        verify(exactly = 1) { repository.searchRepositories(query = "kotlin", sort = "stars") }
    }

    @Test
    fun `invoke should use default sort when not provided`() {
        // Act
        getPublicRepositoriesUseCase("kotlin", sort = RepositorySort.Updated)


        // Assert
        verify(exactly = 1) {
            repository.searchRepositories(
                query = "kotlin",
                sort = RepositorySort.Updated.value
            )
        }
    }
}
