package com.i_sz_app.githubexplorer.presentation.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.i_sz_app.githubexplorer.R
import com.i_sz_app.githubexplorer.presentation.common.composables.CardView
import com.i_sz_app.githubexplorer.presentation.common.composables.Divider
import com.i_sz_app.githubexplorer.presentation.common.theme.GitHubExplorerTheme
import com.i_sz_app.githubexplorer.presentation.common.theme.accentAmber
import com.i_sz_app.githubexplorer.presentation.common.theme.accentBlue
import com.i_sz_app.githubexplorer.presentation.common.theme.accentGreen
import com.i_sz_app.githubexplorer.presentation.details.model.DetailRowData
import java.time.LocalDate

@Composable
fun DetailsCard(detailRows: List<DetailRowData>) {
    CardView {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = stringResource(R.string.details_label),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
                letterSpacing = 1.5.sp,
                modifier = Modifier.padding(bottom = 14.dp)
            )

            detailRows.forEachIndexed { index, row ->
                DetailRow(
                    icon = painterResource(row.iconRes),
                    label = stringResource(row.labelRes),
                    value = row.value,
                    accentColor = row.accentColor
                )
                if (index < detailRows.lastIndex) {
                    Divider()
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsCardPreview() {
    GitHubExplorerTheme {
        DetailsCard(
            detailRows = listOf(
                DetailRowData(
                    iconRes = R.drawable.ic_calendar_month,
                    labelRes = R.string.label_last_updated,
                    value = LocalDate.now().toString(),
                    accentColor = accentGreen
                ),
                DetailRowData(
                    iconRes = R.drawable.ic_calendar_month,
                    labelRes = R.string.label_created,
                    value = LocalDate.now().toString(),
                    accentColor = accentBlue
                ),
                DetailRowData(
                    iconRes = R.drawable.ic_label,
                    labelRes = R.string.label_default_branch,
                    value = "main",
                    accentColor = accentAmber
                )
            )
        )
    }
}
