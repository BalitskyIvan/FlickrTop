package balitsky.flickrtop.data.api

import balitsky.flickrtop.BuildConfig
import balitsky.flickrtop.data.api.model.ImagesListDTO
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ImagesApi {

    @GET("services/rest/?method=flickr.photos.getRecent&api_key=${BuildConfig.API_KEY}&format=json&nojsoncallback=1")
    suspend fun fetchImagesList(): Response<ImagesListDTO>

    @GET("https://live.staticflickr.com/{server}/{id}_{secret}_b.jpg")
    suspend fun fetchImage(
        @Path("server") server: String,
        @Path("id") imageId: String,
        @Path("secret") secret: String
    ): Response<ResponseBody>

}