package com.i_sz_app.githubexplorer.presentation.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.i_sz_app.githubexplorer.domain.model.RepositorySort
import com.i_sz_app.githubexplorer.domain.usecase.BuildRepositoryQueryUseCase
import com.i_sz_app.githubexplorer.domain.usecase.GetPublicRepositoriesUseCase
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class RepositoryViewModel(
    private val getRepositories: GetPublicRepositoriesUseCase,
    private val buildQuery: BuildRepositoryQueryUseCase,
) : ViewModel() {
    val state: StateFlow<RepositoryScreenState>
        field = MutableStateFlow(RepositoryScreenState())

    private val languageInput = MutableSharedFlow<String>(extraBufferCapacity = 1)

    init {
        observeLanguageInput()
        loadRepositories()
    }

    fun onAction(action: RepositoryAction) {
        when (action) {
            RepositoryAction.LoadContent -> loadRepositories()
            RepositoryAction.RefreshContent -> loadRepositories()
            is RepositoryAction.SearchByLanguage -> {
                onLanguageChanged(action.language)
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun observeLanguageInput() {
        viewModelScope.launch {
            languageInput
                .debounce(SEARCH_DEBOUNCE_MS)
                .distinctUntilChanged()
                .collect { language -> collectPaging(buildQuery(language), state.value.sort) }
        }
    }

    private fun onLanguageChanged(language: String) {
        state.update { it.copy(language = language) }
        languageInput.tryEmit(language)
    }

    private fun loadRepositories() {
        collectPaging(buildQuery(state.value.language), sort = state.value.sort)
    }

    private fun collectPaging(
        query: String,
        sort: RepositorySort,
    ) {
        viewModelScope.launch {
            state.update {
                it.copy(contentState = RepositoryContentState.Loading)
            }

            runCatching {
                val pagingFlow = getRepositories(query, sort).cachedIn(viewModelScope)
                state.update {
                    it.copy(
                        contentState = RepositoryContentState.Content(pagingFlow),
                        isRefreshing = false
                    )
                }
            }.onFailure { error ->
                state.update {
                    it.copy(
                        contentState = RepositoryContentState.Error(error),
                        isRefreshing = false
                    )
                }
            }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 500L
    }
}
