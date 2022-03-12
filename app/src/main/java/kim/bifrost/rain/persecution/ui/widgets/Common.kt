package kim.bifrost.rain.persecution.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kim.bifrost.rain.persecution.R

/**
 * kim.bifrost.rain.persecution.ui.widgets.Common
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/10 0:34
 **/
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: String = "https://gitee.com/coldrain-moro/images_bed/raw/master/images/QQ%E6%88%AA%E5%9B%BE20211202011010.png",
    classification: String = "寒雨",
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        onClick = onClick
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .heightIn(min = 75.dp)
                    .align(Alignment.CenterHorizontally),
                painter = rememberImagePainter(
                    image,
                    builder = {
                        placeholder(R.drawable.loading)
                        crossfade(true)
                        scale(Scale.FILL)
                    }
                ),
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(text = classification, modifier = Modifier.align(Alignment.CenterHorizontally))
            }
        }
    }
}

@Composable
fun <T : Any> SwipePagingList(
    items: LazyPagingItems<T>,
    header: (@Composable LazyItemScope.() -> Unit)? = null,
    content: LazyListScope.() -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = false)
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = { items.refresh() }
    ) {

        swipeRefreshState.isRefreshing =
            items.loadState.refresh is LoadState.Loading

        LazyColumn(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            header?.let {
                item(content = header)
            }

            content()

            if (items.itemCount == 0 && items.loadState.refresh is LoadState.NotLoading) {
                item {
                    Image(
                        modifier = Modifier
                            .padding(top = 80.dp)
                            .fillMaxWidth()
                            .height(150.dp),
                        painter = rememberImagePainter(R.drawable.empty_result) {
                            crossfade(true)
                            scale(Scale.FIT)
                        },
                        contentDescription = null
                    )
                }
            }

            items.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        //加载更多时，就在底部显示loading的item
                        item {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                LoadingItem(modifier = Modifier.align(Alignment.CenterHorizontally))
                            }
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        //加载更多的时候出错了，就在底部显示错误的item
                        item {
                            ErrorItem {
                                items.retry()
                            }
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        if (items.itemCount <= 0) {
                            //刷新的时候，如果itemCount小于0，说明是第一次进来，出错了显示一个大的错误内容
                            item {
                                ErrorContent {
                                    items.retry()
                                }
                            }
                        } else {
                            item {
                                ErrorItem {
                                    items.retry()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorItem(retry: () -> Unit) {
    Button(onClick = { retry() }, modifier = Modifier.padding(10.dp)) {
        Text(text = "重试")
    }
}

@Composable
fun ErrorContent(retry: () -> Unit) {
    Image(
        modifier = Modifier
            .padding(top = 80.dp)
            .fillMaxWidth()
            .height(200.dp),
        painter = rememberImagePainter(R.drawable.loading_error) {
            crossfade(true)
            scale(Scale.FIT)
        },
        contentDescription = null
    )
    Button(onClick = { retry() }, modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        Text(text = "重试")
    }
}

@Composable
fun LoadingItem(modifier: Modifier) {
    CircularProgressIndicator(modifier = modifier.padding(10.dp))
}