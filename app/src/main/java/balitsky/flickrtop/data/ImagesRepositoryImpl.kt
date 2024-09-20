package balitsky.flickrtop.data

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import balitsky.flickrtop.data.api.ImagesApi
import balitsky.flickrtop.data.db.ImagesDatabase
import balitsky.flickrtop.data.db.model.ImageDBO
import balitsky.flickrtop.domain.ImagesRepository
import balitsky.flickrtop.domain.model.ImageModel
import balitsky.flickrtop.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException


class ImagesRepositoryImpl(
    private val context: Context,
    private val imagesApi: ImagesApi,
    private val imagesDatabase: ImagesDatabase
) : ImagesRepository {

    private val images = mutableSetOf<ImageModel>()

    override suspend fun fetchImages(isForced: Boolean): Flow<Result<Set<ImageModel>>> {
        return flow {
            emit(Result.Loading)

            if (!isForced) {

                val savedImages = imagesDatabase
                    .imagesDao()
                    .getAll()
                    .map { it.toModel() }
                    .toSet()

                if (savedImages.isNotEmpty()) {
                    images.addAll(savedImages)
                    emit(Result.Success(images))
                }
            }
            try {
                imagesApi.fetchImagesList()
                    .body()
                    ?.photos
                    ?.photo
                    ?.take(20)
                    ?.forEach { imageInfo ->
                        val byteStream = imagesApi.fetchImage(
                            server = imageInfo.server,
                            secret = imageInfo.secret,
                            imageId = imageInfo.id
                        )
                            .body()
                            ?.byteStream()

                        val bitmap = BitmapFactory.decodeStream(byteStream)

                        images.add(
                            ImageModel(
                                id = imageInfo.id,
                                bitmap = bitmap,
                                title = imageInfo.title
                            )
                        )
                        emit(Result.Success(images))
                    }

                if (images.isNotEmpty()) {
                    ContextWrapper(context)
                        .getDir("imageDir", Context.MODE_PRIVATE)
                        .delete()

                    imagesDatabase.imagesDao().saveAll(
                        images.map {
                            saveToInternalStorage(bitmapImage = it.bitmap, id = it.id)

                            ImageDBO(
                                id = it.id,
                                title = it.title
                            )
                        }
                    )
                }

            } catch (e: Exception) {
                  emit(Result.Error(e.message.toString()))
            }
        }
    }

    override suspend fun getImage(id: String): ImageModel? {
        return images.find { it.id == id }
    }

    private fun ImageDBO.toModel(): ImageModel = ImageModel(
        id = id,
        bitmap = loadImageFromStorage(id),
        title = title
    )

    private fun saveToInternalStorage(
        id: String,
        bitmapImage: Bitmap
    ): String {
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val mypath = File(directory, id)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return directory.absolutePath
    }

    private fun loadImageFromStorage(
        id: String,
    ): Bitmap {
        val cw = ContextWrapper(context)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE).absolutePath
        val f = File(directory, id)
        return BitmapFactory.decodeStream(FileInputStream(f))
    }

}
