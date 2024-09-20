package balitsky.flickrtop.domain

import balitsky.flickrtop.domain.model.ImageModel
import balitsky.flickrtop.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    suspend fun fetchImages(isForced: Boolean): Flow<Result<Set<ImageModel>>>

    suspend fun getImage(id: String): ImageModel?
}
