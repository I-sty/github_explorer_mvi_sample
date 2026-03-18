package com.i_sz_app.githubexplorer.presentation.repositories.components

import com.i_sz_app.githubexplorer.domain.exception.GithubException
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.io.IOException

class RefreshErrorToastTest {
    private val rateLimitMsg = "Rate limit exceeded"
    private val noInternetMsg = "No internet connection"
    private val genericMsg = "Something went wrong"

    @Test
    fun `RateLimitExceeded returns rate limit message`() {
        val result = resolveRefreshErrorMessage(
            error = GithubException.RateLimitExceeded(),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals(rateLimitMsg, result)
    }

    @Test
    fun `NoNetwork returns no internet message`() {
        val result = resolveRefreshErrorMessage(
            error = GithubException.NoNetwork(IOException("timeout")),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals(noInternetMsg, result)
    }

    @Test
    fun `unknown error Throwable with message returns error message`() {
        val result = resolveRefreshErrorMessage(
            error = Throwable(message = "Custom error message"),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals("Custom error message", result)
    }

    @Test
    fun `unknown error Exception with message returns error message`() {
        val result = resolveRefreshErrorMessage(
            error = Exception("Custom error message"),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals("Custom error message", result)
    }

    @Test
    fun `unknown error with null message returns generic message`() {
        val result = resolveRefreshErrorMessage(
            error = Exception(),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals(genericMsg, result)
    }

    @Test
    fun `GithubException Unknown Throwable returns its message`() {
        val result = resolveRefreshErrorMessage(
            error = GithubException.Unknown(Throwable(message = "server error")),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals("server error", result)
    }

    @Test
    fun `GithubException Unknown Exception returns its message`() {
        val result = resolveRefreshErrorMessage(
            error = GithubException.Unknown(Exception("server error")),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals("server error", result)
    }

    @Test
    fun `GithubException Unknown with null cause message returns generic`() {
        val result = resolveRefreshErrorMessage(
            error = GithubException.Unknown(Exception()),
            rateLimitMsg = rateLimitMsg,
            noInternetMsg = noInternetMsg,
            genericMsg = genericMsg
        )
        assertEquals(genericMsg, result)
    }
}
