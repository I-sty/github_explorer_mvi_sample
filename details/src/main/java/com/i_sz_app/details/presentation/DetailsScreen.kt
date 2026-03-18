package com.i_sz_app.details.presentation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import com.i_sz_app.core.domain.model.buildRepositoryDetailsModel
import com.i_sz_app.core.presentation.common.composables.AppBarTitle
import com.i_sz_app.core.presentation.common.composables.HeaderCard
import com.i_sz_app.core.presentation.common.theme.GitHubExplorerTheme
import com.i_sz_app.core.presentation.common.theme.accentAmber
import com.i_sz_app.core.presentation.common.theme.accentBlue
import com.i_sz_app.core.presentation.common.theme.accentGreen
import com.i_sz_app.core.presentation.common.theme.accentRed
import com.i_sz_app.core.presentation.common.theme.primaryLight
import com.i_sz_app.details.R
import com.i_sz_app.details.presentation.component.DetailsCard
import com.i_sz_app.details.presentation.component.StatRow
import com.i_sz_app.details.presentation.model.DetailRowData
import com.i_sz_app.details.presentation.model.StatisticRowData
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreenEntry(
    repositoryDetails: RepositoryDetailsModel,
    onNavigateBack: () -> Unit,
    viewModel: DetailsScreenViewModel = koinViewModel(parameters = { parametersOf(repositoryDetails) }),
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        for (effect in viewModel.channels) {
            when (effect) {
                DetailsScreenEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    DetailsScreen(
        state.repositoryDetails,
        state.detailRows,
        state.statisticRows,
        onAction = viewModel::onAction,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedContentScope,
    )
}

@Composable
private fun DetailsScreen(
    repositoryDetails: RepositoryDetailsModel,
    detailRows: List<DetailRowData>,
    statisticRows: List<StatisticRowData>,
    onAction: (DetailsScreenAction) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    AppBarTitle(
        screenTitle = repositoryDetails.name,
        onNavigateBack = { onAction(DetailsScreenAction.GoBack) },
    ) { innerPadding ->

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            with(sharedTransitionScope) {
                HeaderCard(
                    repositoryDetails, modifier = Modifier.sharedElement(
                        rememberSharedContentState(key = "headerCard-${repositoryDetails.id}"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                )
            }

            StatRow(statisticRows)

            DetailsCard(detailRows)
        }
    }
}


@Preview
@Composable
private fun DetailsScreen_Preview() {
    val today = LocalDate.now().toString()
    val repositoryDetails = buildRepositoryDetailsModel()
    val detailsRow = listOf(
        DetailRowData(
            R.drawable.ic_calendar_month,
            R.string.label_last_updated,
            today,
            accentGreen
        ),
        DetailRowData(
            R.drawable.ic_calendar_month,
            R.string.label_created,
            today,
            accentBlue
        ),
        DetailRowData(R.drawable.ic_label, R.string.label_default_branch, "develop", accentAmber),
        DetailRowData(R.drawable.ic_mist, R.string.label_size, "589", primaryLight),
        DetailRowData(
            R.drawable.ic_fork_right,
            R.string.label_forked_repo,
            false.toString(),
            accentRed
        ),
    )
    val statRow = listOf(
        StatisticRowData(
            iconRes = R.drawable.ic_star,
            labelRes = R.string.label_star,
            value = "14",
            accentColor = accentAmber
        ),
        StatisticRowData(
            iconRes = R.drawable.ic_fork_right,
            labelRes = R.string.label_forks,
            value = "5",
            accentColor = accentBlue
        ),
        StatisticRowData(
            iconRes = R.drawable.ic_visibility,
            labelRes = R.string.label_watch,
            value = "45",
            accentColor = accentGreen
        ),
        StatisticRowData(
            iconRes = R.drawable.ic_adjust,
            labelRes = R.string.label_issues,
            value = "3",
            accentColor = accentRed,
        )
    )

    GitHubExplorerTheme {
        SharedTransitionLayout {
            AnimatedVisibility(visible = true) {
                DetailsScreen(
                    repositoryDetails = repositoryDetails,
                    detailRows = detailsRow,
                    statisticRows = statRow,
                    onAction = {},
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this@AnimatedVisibility,
                )
            }
        }
    }
}
