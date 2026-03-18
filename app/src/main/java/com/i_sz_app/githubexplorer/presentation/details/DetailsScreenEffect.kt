package com.i_sz_app.githubexplorer.presentation.details

sealed interface DetailsScreenEffect {
    data object NavigateBack : DetailsScreenEffect
}
