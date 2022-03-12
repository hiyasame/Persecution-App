package kim.bifrost.rain.persecution.ui.square

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kim.bifrost.rain.persecution.logic.SquareViewModel
import kim.bifrost.rain.persecution.ui.theme.titleTextStyle
import kim.bifrost.rain.persecution.ui.widgets.ImageCard
import kim.bifrost.rain.persecution.ui.widgets.SwipePagingList
import kim.bifrost.rain.persecution.utils.getOrNull

/**
 * kim.bifrost.rain.persecution.ui.square.SquareScreen
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/9 20:17
 **/
@Preview
@Composable
fun SquareScreen() {
    val viewModel = viewModel<SquareViewModel>()
    val images = viewModel.pagingData.collectAsLazyPagingItems()
    SwipePagingList(
        items = images,
        header = {
            Text(
                text = "为你推荐",
                style = titleTextStyle,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        },
        content = {
            items(images.itemCount / 2 + 1) { s ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val item = images.getOrNull(s * 2)
                    val item2 = images.getOrNull(s * 2 + 1)
                    item?.let {
                        ImageCard(
                            modifier = Modifier.weight(1.0f),
                            image = item.image,
                        )
                    }
                    item2?.let {
                        ImageCard(
                            modifier = Modifier.weight(1.0f),
                            image = it.image
                        )
                    }
                    if (item2 == null && item != null) {
                        Spacer(modifier = Modifier.weight(1.0f))
                    }
                }
            }
        }
    )
}