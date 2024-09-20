package balitsky.flickrtop.presentation.detailsscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun DetailsScreen(
    id: String,
    navController: NavController,
    viewModel: DetailsScreenViewModel = hiltViewModel()
) {
    viewModel.initWithId(id = id)

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            viewModel.bitmap.collectAsState().value?.let {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = it.asImageBitmap(),
                    contentDescription = "Image"
                )
            }
        }

        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 50.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    navController.popBackStack()
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                contentDescription = "Back Arrow"
            )
        }
    }
}
