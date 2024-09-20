package balitsky.flickrtop.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import balitsky.flickrtop.presentation.detailsscreen.DetailsScreen
import balitsky.flickrtop.presentation.mainscreen.MainScreen

@Composable
fun NavigationStack() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(route = Screen.Main.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.ImageDetail.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            DetailsScreen(
                navController = navController,
                id = it.arguments?.getString("id") ?: ""
            )
        }
    }
}
