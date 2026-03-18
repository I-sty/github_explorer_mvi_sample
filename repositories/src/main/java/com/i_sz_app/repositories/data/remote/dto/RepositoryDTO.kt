package com.i_sz_app.repositories.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDTO(
    val id: Long,
    @SerialName("node_id") val nodeId: String,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val owner: OwnerDTO,
    val private: Boolean,
    @SerialName("html_url") val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("pushed_at") val pushedAt: String,
    val homepage: String?,
    val size: Int,
    @SerialName("stargazers_count") val stargazersCount: Int,
    @SerialName("watchers_count") val watchersCount: Int,
    val language: String?,
    @SerialName("forks_count") val forksCount: Int,
    @SerialName("open_issues_count") val openIssuesCount: Int,
    @SerialName("master_branch") val masterBranch: String? = null,
    @SerialName("default_branch") val defaultBranch: String,
    val score: Double,
    val forks: Int,
    @SerialName("open_issues") val openIssues: Int,
    val watchers: Int,
    @SerialName("has_issues") val hasIssues: Boolean,
    @SerialName("has_projects") val hasProjects: Boolean,
    @SerialName("has_pages") val hasPages: Boolean,
    @SerialName("has_wiki") val hasWiki: Boolean,
    @SerialName("has_downloads") val hasDownloads: Boolean,
    val archived: Boolean,
    val disabled: Boolean,
    val visibility: String,
    val license: LicenseDTO?
)
