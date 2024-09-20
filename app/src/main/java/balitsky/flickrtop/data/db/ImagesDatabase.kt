package balitsky.flickrtop.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import balitsky.flickrtop.data.db.model.ImageDBO

@Database(entities = [ImageDBO::class], version = 1, exportSchema = false)
abstract class ImagesDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
}
