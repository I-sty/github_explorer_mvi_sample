package com.i_sz_app.core.presentation.common.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.i_sz_app.core.presentation.common.theme.GitHubExplorerTheme

@Composable
fun CardView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    // claude.ai => "Each column element (Elevated Card) should display name, owner avatar and short description.
    //Generate 3 design sample about these requirement. I have no idea, I am not a designer, in Jetpack Compose"

    Card(
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
            ),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
            contentColor = MaterialTheme.colorScheme.onSurface,
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun CardView_Preview() {
    GitHubExplorerTheme {
        CardView {
            Box(Modifier.padding(16.dp)) {
                Text("card view  content")
            }
        }
    }
}
