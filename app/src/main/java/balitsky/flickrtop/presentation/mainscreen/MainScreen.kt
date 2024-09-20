package balitsky.flickrtop.presentation.mainscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import balitsky.flickrtop.presentation.navigation.Screen
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainScreenViewModel = hiltViewModel()
) {
    val isLoadingVisible = viewModel.isLoadingVisible.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoadingVisible.value,
        onRefresh = viewModel::fetchNewList
    )

    Box {
        if (isLoadingVisible.value) {
            //TODO Implement loading screen
        } else {
            val error = viewModel.error.collectAsState()

            if (error.value.isEmpty()) {
                Column {
                    Spacer(modifier = Modifier.height(40.dp))

                    ImagesGrid(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState),
                        onImageClicked = { id ->
                            navController.navigate(route = Screen.ImageDetail.route + "?id=${id}")
                        },
                        imageList = viewModel.images
                    )
                }
            } else {
                //TODO Implement error screen
            }
        }

        PullRefreshIndicator(
            refreshing = isLoadingVisible.value,
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
        )
    }
}
