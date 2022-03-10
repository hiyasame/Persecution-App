package kim.bifrost.rain.persecution.model.bean

/**
 * kim.bifrost.rain.persecution.model.bean.BaseResponse
 * persecution
 *
 * @author 寒雨
 * @since 2022/3/5 14:38
 **/
data class BaseResponse<T>(
    // 正常时为0
    val errorCode: Int,
    val message: String,
    val data: T
)