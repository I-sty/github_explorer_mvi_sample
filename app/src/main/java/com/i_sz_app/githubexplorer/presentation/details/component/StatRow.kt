package com.i_sz_app.githubexplorer.presentation.details.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.i_sz_app.githubexplorer.presentation.details.model.StatisticRowData

@Composable
fun StatRow(statisticRows: List<StatisticRowData>) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        statisticRows.forEach { row ->
            StatChip(
                modifier = Modifier.weight(1f),
                icon = painterResource(row.iconRes),
                label = stringResource(row.labelRes),
                value = row.value,
                accentColor = row.accentColor
            )
        }
    }
}
