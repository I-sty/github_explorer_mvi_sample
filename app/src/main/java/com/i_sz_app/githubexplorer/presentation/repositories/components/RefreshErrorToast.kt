package com.i_sz_app.githubexplorer.presentation.repositories.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import com.i_sz_app.githubexplorer.R
import com.i_sz_app.githubexplorer.domain.exception.GithubException

@Composable
fun RefreshErrorToast(
    refreshState: LoadState,
    hasCachedData: Boolean,
) {
    val context = LocalContext.current
    val rateLimitMessage = stringResource(R.string.error_rate_limit)
    val noInternetMessage = stringResource(R.string.error_no_network)
    val genericErrorMessage = stringResource(R.string.error_something_went_wrong)

    LaunchedEffect(refreshState) {
        if (refreshState is Error && hasCachedData) {
            val message = resolveRefreshErrorMessage(
                error = refreshState.error,
                rateLimitMsg = rateLimitMessage,
                noInternetMsg = noInternetMessage,
                genericMsg = genericErrorMessage
            )
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }
}


// added here to test the when logic
internal fun resolveRefreshErrorMessage(
    error: Throwable,
    rateLimitMsg: String,
    noInternetMsg: String,
    genericMsg: String,
): String = when (error) {
    is GithubException.RateLimitExceeded -> rateLimitMsg
    is GithubException.NoNetwork -> noInternetMsg
    is GithubException.Unknown -> error.cause.message ?: genericMsg
    is Throwable -> error.localizedMessage ?: genericMsg
}
