package com.i_sz_app.githubexplorer.presentation.common.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.i_sz_app.githubexplorer.core.util.letIf
import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.domain.model.buildRepositoryDetailsModel
import com.i_sz_app.githubexplorer.presentation.common.theme.GitHubExplorerTheme

@Composable
fun HeaderCard(
    repository: RepositoryDetailsModel,
    modifier: Modifier = Modifier,
    onRepositoryClick: ((RepositoryDetailsModel) -> Unit)? = null,
) {
    CardView(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                clip = true,
            )
            .fillMaxWidth()
            .letIf(onRepositoryClick != null) {
                it.clickable(
                    onClick = {
                        onRepositoryClick(repository)
                    },
                )
            },
    ) {
        AvatarRow(repository)

        if (!repository.description.isNullOrEmpty()) {

            Divider()

            Text(
                text = repository.description,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun HeaderCard_Preview() {
    GitHubExplorerTheme {
        HeaderCard(buildRepositoryDetailsModel())
    }
}
