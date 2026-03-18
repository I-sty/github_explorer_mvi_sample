package com.i_sz_app.repositories.domain.exception

sealed class GithubException : Exception() {
    class RateLimitExceeded : GithubException()
    class NotFound : GithubException()
    class NotModified : GithubException()
    class ValidationFailed : GithubException()
    class ServiceUnavailable : GithubException()
    data class NoNetwork(override val cause: Throwable) : GithubException()
    data class Unknown(override val cause: Throwable) : GithubException()
}
