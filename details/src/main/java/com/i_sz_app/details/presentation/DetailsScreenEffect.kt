package com.i_sz_app.details.presentation

sealed interface DetailsScreenEffect {
    data object NavigateBack : DetailsScreenEffect
}
