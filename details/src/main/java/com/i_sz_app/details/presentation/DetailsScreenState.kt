package com.i_sz_app.details.presentation

import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import com.i_sz_app.details.presentation.model.DetailRowData
import com.i_sz_app.details.presentation.model.StatisticRowData

data class DetailsScreenState(
    val repositoryDetails: RepositoryDetailsModel,
    val detailRows: List<DetailRowData>,
    val statisticRows: List<StatisticRowData>,
)
