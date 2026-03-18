package com.i_sz_app.githubexplorer.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.i_sz_app.core.presentation.common.theme.GitHubExplorerTheme
import com.i_sz_app.githubexplorer.core.navigation.NavigationStack

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GitHubExplorerTheme {
                NavigationStack()
            }
        }
    }
}
