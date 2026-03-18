package com.i_sz_app.githubexplorer.presentation.details

sealed interface DetailsScreenAction {
    data object GoBack : DetailsScreenAction
}
