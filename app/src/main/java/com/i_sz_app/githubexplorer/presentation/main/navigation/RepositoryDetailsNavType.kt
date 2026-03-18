package com.i_sz_app.githubexplorer.presentation.main.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.i_sz_app.core.domain.model.RepositoryDetailsModel
import kotlinx.serialization.json.Json

val RepositoryDetailsNavType = object : NavType<RepositoryDetailsModel>(
    isNullableAllowed = false
) {
    private val json = Json { ignoreUnknownKeys = true }

    override fun get(bundle: Bundle, key: String): RepositoryDetailsModel? =
        bundle.getString(key)?.let { json.decodeFromString(it) }

    override fun parseValue(value: String): RepositoryDetailsModel =
        json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: RepositoryDetailsModel): String =
        Uri.encode(json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: RepositoryDetailsModel) =
        bundle.putString(key, json.encodeToString(value))
}
