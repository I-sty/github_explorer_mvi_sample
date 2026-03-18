package com.i_sz_app.githubexplorer.presentation.repositories

import androidx.paging.PagingData
import com.i_sz_app.githubexplorer.MainDispatcherRule
import com.i_sz_app.githubexplorer.domain.model.RepositorySort
import com.i_sz_app.githubexplorer.domain.model.buildRepositoryDetailsModel
import com.i_sz_app.githubexplorer.domain.usecase.BuildRepositoryQueryUseCase
import com.i_sz_app.githubexplorer.domain.usecase.GetPublicRepositoriesUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class RepositoryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getRepositoriesUseCase = mockk<GetPublicRepositoriesUseCase>()
    private val buildQueryUseCase = mockk<BuildRepositoryQueryUseCase>()
    private lateinit var viewModel: RepositoryViewModel

    @Before
    fun setup() {
        val fakePagingData = PagingData.from(List(5) { buildRepositoryDetailsModel(it.toLong()) })
        every { getRepositoriesUseCase(any(), any()) } returns flowOf(fakePagingData)
        every { buildQueryUseCase(any()) } answers { firstArg() }
        viewModel = RepositoryViewModel(getRepositoriesUseCase, buildQueryUseCase)
    }

    @Test
    fun `LoadContent transitions to Content state`() = runTest {
        // Act
        viewModel.onAction(RepositoryAction.LoadContent)

        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.state.value.contentState is RepositoryContentState.Content)
        assertFalse(viewModel.state.value.isRefreshing)
    }

    @Test
    fun `RefreshContent transitions to Content state`() = runTest {
        // Act
        viewModel.onAction(RepositoryAction.RefreshContent)

        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.state.value.contentState is RepositoryContentState.Content)
        assertFalse(viewModel.state.value.isRefreshing)
    }

    @Test
    fun `RefreshContent transitions to Error state`() = runTest {
        // Arrange
        every { getRepositoriesUseCase(any(), any()) } throws Throwable("custom exception")

        // Act
        viewModel.onAction(RepositoryAction.RefreshContent)

        advanceUntilIdle()

        // Assert
        assertTrue(viewModel.state.value.contentState is RepositoryContentState.Error)
        assertFalse(viewModel.state.value.isRefreshing)
    }

    @Test
    fun `SearchByLanguage debounces and builds correct query`() = runTest {
        // Arrange
        val language = "kotlin"
        val expectedQuery = "size:>0 language:kotlin"
        every { buildQueryUseCase(language) } returns expectedQuery

        // Act
        viewModel.onAction(RepositoryAction.SearchByLanguage(language))

        advanceUntilIdle()

        // Assert
        verify(exactly = 1) {
            getRepositoriesUseCase(query = expectedQuery, sort = RepositorySort.Stars)
        }
    }
}
