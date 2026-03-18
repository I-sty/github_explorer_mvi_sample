package com.i_sz_app.githubexplorer.presentation.main.navigation

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.i_sz_app.repositories.presentation.RepositoryScreenEntry
import kotlin.reflect.typeOf

@Composable
fun NavigationStack(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {

    val typeMap = mapOf(typeOf<RepositoryDetailsModel>() to RepositoryDetailsNavType)

    SharedTransitionLayout {
        NavHost(
            navController = navController,
            startDestination = Screen.Repositories,
            modifier = modifier,
        ) {
            composable<Screen.Repositories> {
                RepositoryScreenEntry(
                    onRepositoryClick = { repositoryDetails: RepositoryDetailsModel ->
                        navController.navigate(Screen.Details(repositoryDetails))
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                )
            }

            composable<Screen.Details>(typeMap = typeMap) { backStackEntry ->
                val screen = backStackEntry.toRoute<Screen.Details>()
                DetailsScreenEntry(
                    screen.repositoryDetails,
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedContentScope = this@composable,
                )
            }
        }
    }
}
