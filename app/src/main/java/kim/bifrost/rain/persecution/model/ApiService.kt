package kim.bifrost.rain.persecution.model

import kim.bifrost.rain.persecution.model.bean.BaseResponse
import kim.bifrost.rain.persecution.model.bean.ClassificationData
import kim.bifrost.rain.persecution.model.bean.Pager
import kim.bifrost.rain.persecution.model.bean.SingleImageData
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import retrofit2.http.*

/**
 * kim.bifrost.rain.persecution.model.ApiService
 * Persecution
 * https://github.com/ColdRain-Moro/Persecution-Backend/blob/master/API.md
 *
 * @author 寒雨
 * @since 2022/3/9 9:13
 **/
interface ApiService {

    @GET("/classification")
    suspend fun getClassification(
        @Query("id") id: Int? = null,
        @Query("name") name: String? = null
    ): BaseResponse<ClassificationData>

    @FormUrlEncoded
    @POST("/classification/upload")
    suspend fun uploadToClassification(
        @Field("cid") cid: Int,
        @Field("image") image: String
    ): BaseResponse<String>

    @GET("/classification/images")
    suspend fun getImages(
        @Query("id") id: Int? = null,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : BaseResponse<Pager<SingleImageData>>

    @FormUrlEncoded
    @POST("/classification/create")
    suspend fun createClassification(
        @Field("name") name: String,
        @Field("avatar") avatar: String,
        @Field("description") description: String
    ) : BaseResponse<String>

    @FormUrlEncoded
    @POST("/classification/remove")
    fun removeClassification(
        @Field("id") id: Int
    ) : Flow<BaseResponse<String>>

    @GET("/classification/list")
    suspend fun getAllClassification(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : BaseResponse<List<ClassificationData>>

    @GET("/classification/query")
    suspend fun searchClassification(
        @Query("query") query: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : BaseResponse<Pager<ClassificationData>>

    @GET("/image")
    suspend fun getImage(
        @Query("id") id: Int
    ) : BaseResponse<SingleImageData>

    @Multipart
    @POST("/upload")
    suspend fun upload(
        @Part image: MultipartBody.Part
    ): BaseResponse<String>

    companion object : ApiService by RetrofitHelper.service
}