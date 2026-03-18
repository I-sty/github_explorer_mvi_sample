package com.i_sz_app.details.presentation

sealed interface DetailsScreenAction {
    data object GoBack : DetailsScreenAction
}
