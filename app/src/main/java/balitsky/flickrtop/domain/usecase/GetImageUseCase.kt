package balitsky.flickrtop.domain.usecase

import balitsky.flickrtop.domain.ImagesRepository
import balitsky.flickrtop.domain.model.ImageModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetImageUseCase @Inject constructor(
    private val imagesRepository: ImagesRepository
) {

    suspend operator fun invoke(id: String): ImageModel? {
        return imagesRepository
            .getImage(id)
    }
}