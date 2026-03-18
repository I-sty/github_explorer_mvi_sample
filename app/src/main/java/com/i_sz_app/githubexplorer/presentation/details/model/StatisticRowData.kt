package com.i_sz_app.githubexplorer.presentation.details.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class StatisticRowData(
    @field:DrawableRes val iconRes: Int,
    @field:StringRes val labelRes: Int,
    val value: String,
    val accentColor: Color,
)
