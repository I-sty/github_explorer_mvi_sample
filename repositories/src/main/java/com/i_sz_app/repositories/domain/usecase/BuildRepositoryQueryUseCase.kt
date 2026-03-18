package com.i_sz_app.repositories.domain.usecase

import org.koin.core.annotation.Single

/**
 * https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-repositories
 *
 * "" The query contains one or more search keywords and qualifiers.  ""
 * According to the official documentation searching without any keyword is not valid.
 * I decided to include "size:>0" as the default query keyword.
 *
 * */
@Single
class BuildRepositoryQueryUseCase {
    operator fun invoke(language: String): String =
        if (language.isBlank()) DEFAULT_QUERY
        else "$DEFAULT_QUERY language:${language.trim().lowercase()}"

    companion object {
        private const val DEFAULT_QUERY = "size:>0"
    }
}
