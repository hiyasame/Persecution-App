package kim.bifrost.rain.persecution.utils

import androidx.paging.compose.LazyPagingItems

/**
 * kim.bifrost.rain.persecution.utils.Utils
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/11 22:56
 **/
fun <T : Any> LazyPagingItems<T>.getOrNull(slot: Int): T? {
    if (slot >= itemCount) return null
    return get(slot)
}