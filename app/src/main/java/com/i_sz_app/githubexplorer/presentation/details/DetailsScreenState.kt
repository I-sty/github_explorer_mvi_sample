package com.i_sz_app.githubexplorer.presentation.details

import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.presentation.details.model.DetailRowData
import com.i_sz_app.githubexplorer.presentation.details.model.StatisticRowData

data class DetailsScreenState(
    val repositoryDetails: RepositoryDetailsModel,
    val detailRows: List<DetailRowData>,
    val statisticRows: List<StatisticRowData>,
)
