package balitsky.flickrtop.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageDBO(
    @PrimaryKey
    val id: String,
    val title: String
)
