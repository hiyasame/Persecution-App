package kim.bifrost.rain.persecution.logic

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kim.bifrost.rain.persecution.model.ApiService
import kim.bifrost.rain.persecution.model.BasePagingSource

/**
 * kim.bifrost.rain.persecution.logic.ClassificationViewModel
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/11 21:52
 **/
class ClassificationViewModel : ViewModel() {

    val pagingData = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            BasePagingSource {
                ApiService.getAllClassification(offset = it * 20).data
            }
        }
    ).flow.cachedIn(viewModelScope)

}