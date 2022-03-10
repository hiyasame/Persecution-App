package kim.bifrost.rain.persecution.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kim.bifrost.rain.persecution.model.ApiService
import kim.bifrost.rain.persecution.model.BasePagingSource
import kim.bifrost.rain.persecution.model.bean.ClassificationData
import kim.bifrost.rain.persecution.model.bean.SingleImageData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

/**
 * kim.bifrost.rain.persecution.logic.SquareViewModel
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/9 20:18
 **/
class SquareViewModel : ViewModel() {

    val pagingData = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            BasePagingSource {
                ApiService.getImages(offset = it * 20).data.data
            }
        }
    ).flow.cachedIn(viewModelScope)

}