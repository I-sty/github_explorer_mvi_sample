package com.i_sz_app.repositories.domain.usecase

import androidx.paging.PagingData
import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import com.i_sz_app.repositories.domain.model.RepositorySort
import com.i_sz_app.repositories.domain.repository.IRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class GetPublicRepositoriesUseCase(private val repository: IRepository) {
    operator fun invoke(
        query: String,
        sort: RepositorySort,
    ): Flow<PagingData<RepositoryDetailsModel>> =
        repository.searchRepositories(query, sort.value)
}
