package com.i_sz_app.githubexplorer.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.i_sz_app.githubexplorer.R
import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.presentation.common.theme.accentAmber
import com.i_sz_app.githubexplorer.presentation.common.theme.accentBlue
import com.i_sz_app.githubexplorer.presentation.common.theme.accentGreen
import com.i_sz_app.githubexplorer.presentation.common.theme.accentRed
import com.i_sz_app.githubexplorer.presentation.common.theme.primaryLight
import com.i_sz_app.githubexplorer.presentation.details.model.DetailRowData
import com.i_sz_app.githubexplorer.presentation.details.model.StatisticRowData
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.InjectedParam

@KoinViewModel
class DetailsScreenViewModel(
    @InjectedParam val repositoryModel: RepositoryDetailsModel,
) : ViewModel() {

    val channels = Channel<DetailsScreenEffect>()

    val state: StateFlow<DetailsScreenState>
        field = MutableStateFlow<DetailsScreenState>(
            DetailsScreenState(
                repositoryModel,
                detailRows = repositoryModel.toDetailsRows(),
                statisticRows = repositoryModel.toStatisticRows()
            )
        )

    private fun RepositoryDetailsModel.toDetailsRows(): List<DetailRowData> {
        return listOf(
            DetailRowData(
                iconRes = R.drawable.ic_calendar_month,
                labelRes = R.string.label_last_updated,
                value = updatedAt,
                accentColor = accentGreen
            ),
            DetailRowData(
                iconRes = R.drawable.ic_calendar_month,
                labelRes = R.string.label_created,
                value = createdAt,
                accentColor = accentBlue
            ),
            DetailRowData(
                iconRes = R.drawable.ic_label,
                labelRes = R.string.label_default_branch,
                value = defaultBranchName,
                accentColor = accentAmber
            ),
            DetailRowData(
                iconRes = R.drawable.ic_mist,
                labelRes = R.string.label_size,
                value = formatCount(size),
                accentColor = primaryLight
            ),
            DetailRowData(
                iconRes = R.drawable.ic_fork_right,
                labelRes = R.string.label_forked_repo,
                value = isForked.toString(),
                accentColor = accentRed
            ),
        )
    }

    private fun RepositoryDetailsModel.toStatisticRows(): List<StatisticRowData> {
        return listOf(
            StatisticRowData(
                iconRes = R.drawable.ic_star,
                labelRes = R.string.label_star,
                value = formatCount(stargazersCount),
                accentColor = accentAmber
            ),
            StatisticRowData(
                iconRes = R.drawable.ic_fork_right,
                labelRes = R.string.label_forks,
                value = formatCount(forksCount),
                accentColor = accentBlue
            ),
            StatisticRowData(
                iconRes = R.drawable.ic_visibility,
                labelRes = R.string.label_watch,
                value = formatCount(watchersCount),
                accentColor = accentGreen
            ),
            StatisticRowData(
                iconRes = R.drawable.ic_adjust,
                labelRes = R.string.label_issues,
                value = formatCount(openIssuesCount),
                accentColor = accentRed,
            )
        )
    }

    fun onAction(action: DetailsScreenAction) {
        when (action) {
            DetailsScreenAction.GoBack -> {
                viewModelScope.launch {
                    channels.send(DetailsScreenEffect.NavigateBack)
                }
            }
        }
    }

    private fun formatCount(count: Int): String = when {
        count >= 1_000 -> "${"%.1f".format(count / 1000.0)}k"
        else -> count.toString()
    }
}
