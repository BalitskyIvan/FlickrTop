package balitsky.flickrtop.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import balitsky.flickrtop.data.db.model.ImageDBO

@Dao
interface ImagesDao {

    @Query("SELECT * FROM ImageDBO")
    fun getAll(): List<ImageDBO>

    @Insert
    fun saveAll(images: List<ImageDBO>)
}
