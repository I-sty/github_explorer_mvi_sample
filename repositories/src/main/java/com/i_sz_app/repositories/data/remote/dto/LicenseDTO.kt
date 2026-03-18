package com.i_sz_app.repositories.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LicenseDTO(
    val key: String,
    val name: String,
    val url: String?,
    @SerialName("spdx_id") val spdxId: String,
    @SerialName("node_id") val nodeId: String,
    @SerialName("html_url") val htmlUrl: String? = null
)
