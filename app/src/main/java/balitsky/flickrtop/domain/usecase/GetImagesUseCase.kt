package balitsky.flickrtop.domain.usecase

import balitsky.flickrtop.domain.ImagesRepository
import balitsky.flickrtop.domain.model.Result
import balitsky.flickrtop.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImagesUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend operator fun invoke(isForced: Boolean = false): Flow<Result<List<ImageModel>>> {
        return imagesRepository
            .fetchImages(isForced)
            .map {
                if (it is Result.Success) {
                    Result.Success(it.data.toList())
                } else {
                    Result.Error("")
                }
            }
    }
}
