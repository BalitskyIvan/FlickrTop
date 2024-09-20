package balitsky.flickrtop.presentation.navigation

sealed class Screen(val route: String) {
    data object Main: Screen("main_screen")
    data object ImageDetail: Screen("image_detail_screen")
}
