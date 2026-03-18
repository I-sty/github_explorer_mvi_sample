package com.i_sz_app.githubexplorer.presentation.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.i_sz_app.githubexplorer.R
import com.i_sz_app.githubexplorer.presentation.common.composables.CardView
import com.i_sz_app.githubexplorer.presentation.common.theme.GitHubExplorerTheme
import com.i_sz_app.githubexplorer.presentation.common.theme.accentAmber

@Composable
fun StatChip(
    modifier: Modifier = Modifier,
    icon: Painter,
    value: String,
    label: String,
    accentColor: Color,
) {
    CardView(modifier) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = label,
                tint = accentColor,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
            )
            Text(
                text = label,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium,
            )
        }
    }
}

@Preview
@Composable
private fun StatChip_Preview() {
    GitHubExplorerTheme {
        StatChip(
            icon = painterResource(R.drawable.ic_star),
            label = stringResource(R.string.label_star),
            value = "123",
            accentColor = accentAmber
        )
    }
}
