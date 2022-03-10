package kim.bifrost.rain.persecution.model.bean

/**
 * kim.bifrost.rain.persecution.model.bean.Pager
 * persecution
 * 分页
 *
 * @author 寒雨
 * @since 2022/3/5 23:46
 **/
data class Pager<T>(
    val size: Int,
    val offset: Int,
    val data: List<T>
)