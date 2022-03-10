package kim.bifrost.rain.persecution.ui.square

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import kim.bifrost.rain.persecution.logic.SquareViewModel
import kim.bifrost.rain.persecution.ui.theme.titleTextStyle
import kim.bifrost.rain.persecution.ui.widgets.ImageCard

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
    LazyColumn(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        item {
            Text(
                text = "为你推荐",
                style = titleTextStyle,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
        items(images.itemCount / 2) { s ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                val item = images[s * 2]
                val item2 = images[s * 2 + 1]
                ImageCard(
                    image = item!!.image,
                    imageId = item.id,
                )
                item2?.let {
                    ImageCard(
                        image = it.image,
                        imageId = it.id,
                    )
                }
            }
        }
    }
}