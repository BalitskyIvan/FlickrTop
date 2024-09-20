package balitsky.flickrtop.domain.model

import android.graphics.Bitmap

data class ImageModel(
    val id: String,
    val bitmap: Bitmap,
    val title: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageModel

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
