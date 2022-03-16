package kim.bifrost.rain.persecution.ui.square

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.placeholder.material.placeholder
import kim.bifrost.rain.persecution.ui.Screen
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
fun SquareScreen(navController: NavController = rememberNavController()) {
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
                            modifier = Modifier.weight(1.0f, fill = false),
                            image = item.first.image,
                            onClick = {
                                navController.navigate(Screen.Image.generateRoute("id" to it.first.id))
                            },
                            classification = it.second.name
                        )
                    }
                    item2?.let {
                        ImageCard(
                            modifier = Modifier.weight(1.0f, fill = false),
                            image = it.first.image,
                            onClick = {
                                navController.navigate(Screen.Image.generateRoute("id" to it.first.id))
                            },
                            classification = it.second.name
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