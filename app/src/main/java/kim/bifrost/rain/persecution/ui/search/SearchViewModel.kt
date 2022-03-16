package kim.bifrost.rain.persecution.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kim.bifrost.rain.persecution.model.ApiService
import kim.bifrost.rain.persecution.model.BasePagingSource

/**
 * kim.bifrost.rain.persecution.ui.search.SearchViewModel
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/16 22:11
 **/
class SearchViewModel : ViewModel() {
    fun query(q: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            BasePagingSource {
                ApiService.searchClassification(query = q, offset = it * 20).data.data.also { Log.d("Test", "(SearchViewModel.kt:27) ==> 1") }
            }
        }
    ).flow.cachedIn(viewModelScope)
}