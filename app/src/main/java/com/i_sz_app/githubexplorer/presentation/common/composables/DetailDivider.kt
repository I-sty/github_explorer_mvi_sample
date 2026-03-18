package com.i_sz_app.githubexplorer.presentation.common.composables

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Divider(
    modifier: Modifier = Modifier,
    thickness: Dp = 0.5.dp,
    color: Color = MaterialTheme.colorScheme.outlineVariant,
) {
    HorizontalDivider(modifier, thickness, color)
}
