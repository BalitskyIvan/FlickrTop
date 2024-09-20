package balitsky.flickrtop.data.api.model

data class ImagesListDTO(
    val photos: ImagesDTO
)

data class ImagesDTO(
    val photo: List<ImageDTO>
)
