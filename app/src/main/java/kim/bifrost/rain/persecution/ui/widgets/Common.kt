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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kim.bifrost.rain.persecution.R
import kotlin.math.ceil

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
            .padding(10.dp)
            .wrapContentHeight(),
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
                        scale(Scale.FIT)
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
                Text(
                    text = classification,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun CardImage(
    modifier: Modifier = Modifier,
    image: String = "https://gitee.com/coldrain-moro/images_bed/raw/master/images/QQ%E6%88%AA%E5%9B%BE20211202011010.png",
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .wrapContentHeight(),
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
                painter = rememberImagePainter(image) {
                    crossfade(true)
                },
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun <T : Any> SwipePagingList(
    items: LazyPagingItems<T>,
    header: (@Composable LazyItemScope.() -> Unit)? = null,
    content: LazyListScope.(SwipeRefreshState) -> Unit
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

            content(swipeRefreshState)

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
                        (loadState.refresh as LoadState.Error).error.printStackTrace()
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

@Composable
fun StaggeredVerticalGrid(
    modifier: Modifier = Modifier,
    maxColumnWidth: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        check(constraints.hasBoundedWidth) {
            "Unbounded width not supported"
        }
        val columns = ceil(constraints.maxWidth / maxColumnWidth.toPx()).toInt()
        val columnWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = columnWidth)
        val colHeights = IntArray(columns) { 0 } // track each column's height
        val placeables = measurables.map { measurable ->
            val column = shortestColumn(colHeights)
            val placeable = measurable.measure(itemConstraints)
            colHeights[column] += placeable.height
            placeable
        }

        val height = colHeights.maxOrNull()?.coerceIn(constraints.minHeight, constraints.maxHeight)
            ?: constraints.minHeight
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            val colY = IntArray(columns) { 0 }
            placeables.forEach { placeable ->
                val column = shortestColumn(colY)
                placeable.place(
                    x = columnWidth * column,
                    y = colY[column]
                )
                colY[column] += placeable.height
            }
        }
    }
}

private fun shortestColumn(colHeights: IntArray): Int {
    var minHeight = Int.MAX_VALUE
    var column = 0
    colHeights.forEachIndexed { index, height ->
        if (height < minHeight) {
            minHeight = height
            column = index
        }
    }
    return column
}