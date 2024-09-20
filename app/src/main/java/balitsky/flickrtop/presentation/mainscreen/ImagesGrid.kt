package balitsky.flickrtop.presentation.mainscreen

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import balitsky.flickrtop.domain.model.ImageModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ImagesGrid(
    modifier: Modifier,
    imageList: StateFlow<List<ImageModel>>,
    onImageClicked: (id: String) -> Unit
) {
    val images = imageList.collectAsState().value

    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            LazyVerticalGrid(
                modifier = modifier
                    .padding(horizontal = 10.dp),
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(images) { image ->
                    Image(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .size(200.dp)
                            .clickable {
                                onImageClicked.invoke(image.id)
                            },
                        bitmap = image.bitmap.asImageBitmap(),
                        contentScale = ContentScale.Crop,
                        contentDescription = "Image"
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }

        else -> LazyVerticalGrid(
            modifier = modifier
                .padding(horizontal = 10.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(images) { image ->
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .size(200.dp)
                        .clickable {
                            onImageClicked.invoke(image.id)
                        },
                    bitmap = image.bitmap.asImageBitmap(),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Image"
                )
            }

            item {
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}
