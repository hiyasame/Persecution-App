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
    fun getClassification(
        @Query("id") id: Int,
        @Query("name") name: String
    ): Flow<BaseResponse<ClassificationData>>

    @FormUrlEncoded
    @POST("/classification/upload")
    fun uploadToClassification(
        @Field("cid") cid: Int,
        @Field("image") image: String
    ): Flow<BaseResponse<String>>

    @GET("/classification/images")
    suspend fun getImages(
        @Query("id") id: Int? = null,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : BaseResponse<Pager<SingleImageData>>

    @FormUrlEncoded
    @POST("/classification/create")
    fun createClassification(
        @Field("name") name: String,
        @Field("avatar") avatar: String,
        @Field("description") description: String
    ) : Flow<BaseResponse<String>>

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
    fun searchClassification(
        @Query("query") query: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ) : Flow<BaseResponse<Pager<ClassificationData>>>

    @Multipart
    @POST("/upload")
    fun upload(
        @Part("image") image: MultipartBody.Part
    ): Flow<BaseResponse<String>>

    companion object : ApiService by RetrofitHelper.service
}