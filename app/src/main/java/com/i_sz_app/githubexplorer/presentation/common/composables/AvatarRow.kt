package com.i_sz_app.githubexplorer.presentation.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.i_sz_app.githubexplorer.R
import com.i_sz_app.githubexplorer.domain.model.RepositoryDetailsModel
import com.i_sz_app.githubexplorer.domain.model.buildRepositoryDetailsModel
import com.i_sz_app.githubexplorer.presentation.common.theme.GitHubExplorerTheme

@Composable
fun AvatarRow(
    repository: RepositoryDetailsModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (repository.ownerAvatar.isEmpty()) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(color = MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = repository.ownerName,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                )
            }
        } else {
            AsyncImage(
                model = repository.ownerAvatar,
                contentDescription = stringResource(R.string.content_desc_user_avatar),
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp)),
                fallback = painterResource(R.drawable.ic_person)
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = repository.name,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                letterSpacing = (-0.3).sp
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = repository.ownerName,
                    color = MaterialTheme.colorScheme.outline,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 2,
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AvatarRow_AvatarNotNullPreview() {
    val repo = buildRepositoryDetailsModel()

    GitHubExplorerTheme {
        AvatarRow(repo)
    }
}

@Preview(showBackground = true)
@Composable
fun AvatarRow_AvatarNullPreview() {
    val repo = buildRepositoryDetailsModel(ownerAvatar = "")

    GitHubExplorerTheme {
        AvatarRow(repo)
    }
}
