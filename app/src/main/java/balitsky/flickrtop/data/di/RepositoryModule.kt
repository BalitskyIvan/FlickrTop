package balitsky.flickrtop.data.di

import android.content.Context
import androidx.room.Room
import balitsky.flickrtop.data.api.ImagesApi
import balitsky.flickrtop.domain.ImagesRepository
import balitsky.flickrtop.data.ImagesRepositoryImpl
import balitsky.flickrtop.data.db.ImagesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideImagesDatabase(
        @ApplicationContext
        context: Context
    ): ImagesDatabase = Room.databaseBuilder(context, ImagesDatabase::class.java, "images-database").build()

    @Provides
    @Singleton
    fun provideImagesRepository(
        @ApplicationContext
        context: Context,
        imagesDatabase: ImagesDatabase,
        imagesApi: ImagesApi
    ): ImagesRepository = ImagesRepositoryImpl(
        context = context,
        imagesApi = imagesApi,
        imagesDatabase = imagesDatabase
    )

}
