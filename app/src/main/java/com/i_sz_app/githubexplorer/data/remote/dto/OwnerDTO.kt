package com.i_sz_app.githubexplorer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerDTO(
    val login: String,
    val id: Long,
    @SerialName("node_id") val nodeId: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("gravatar_id") val gravatarId: String? = null,
    val url: String,
    @SerialName("html_url") val htmlUrl: String? = null,
    @SerialName("followers_url") val followersUrl: String? = null,
    @SerialName("following_url") val followingUrl: String? = null,
    @SerialName("gists_url") val gistsUrl: String? = null,
    @SerialName("starred_url") val starredUrl: String? = null,
    @SerialName("subscriptions_url") val subscriptionsUrl: String? = null,
    @SerialName("organizations_url") val organizationsUrl: String? = null,
    @SerialName("repos_url") val reposUrl: String? = null,
    @SerialName("events_url") val eventsUrl: String? = null,
    @SerialName("received_events_url") val receivedEventsUrl: String? = null,
    val type: String,
    @SerialName("site_admin") val siteAdmin: Boolean
)
