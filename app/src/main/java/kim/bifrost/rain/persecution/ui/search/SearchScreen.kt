package kim.bifrost.rain.persecution.ui.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kim.bifrost.rain.persecution.model.bean.ClassificationData
import kim.bifrost.rain.persecution.ui.Screen
import kim.bifrost.rain.persecution.ui.classification.ClassificationItem
import kim.bifrost.rain.persecution.ui.theme.titleTextStyle
import kim.bifrost.rain.persecution.ui.widgets.SwipePagingList
import kim.bifrost.rain.persecution.utils.getOrNull
import kotlinx.coroutines.flow.emptyFlow

/**
 * kim.bifrost.rain.persecution.ui.search.SearchScrenn
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/12 16:04
 **/
@Preview
@Composable
fun SearchScreen(navController: NavController = rememberNavController()) {
    val viewModel = viewModel<SearchViewModel>()
    var query by remember { mutableStateOf("") }
    // 如果没有关键词就不搜索 给一个空Flow
    val items = if (query.isNotEmpty())
        viewModel.query(query).collectAsLazyPagingItems()
    else
        emptyFlow<PagingData<ClassificationData>>().collectAsLazyPagingItems()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                contentColor = Color.Gray,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = { },
                title = {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .padding(end = 10.dp)
                                .fillMaxHeight()
                                .fillMaxWidth(0.8f),
                            value = query,
                            onValueChange = { query = it },
                            singleLine = true,
                            placeholder = { Text(text = "搜索分类") },
                            textStyle = TextStyle(
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )
                        IconButton(onClick = {  }) {
                            Icon(Icons.Filled.Search, contentDescription = null)
                        }
                    }
                }
            )
        }
    ) {
        SwipePagingList(
            items = items,
            content = {
                items(items.itemCount / 2 + 1) { s ->
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            val item = items.getOrNull(s * 2)
                            val item2 = items.getOrNull(s * 2 + 1)
                            item?.let {
                                ClassificationItem(
                                    modifier = Modifier.weight(1f),
                                    data = item,
                                    onClick = {
                                        navController.navigate(Screen.Classification.generateRoute("id" to item.id))
                                    }
                                )
                            }
                            item2?.let {
                                ClassificationItem(
                                    modifier = Modifier.weight(1f),
                                    data = item2,
                                    onClick = {
                                        navController.navigate(Screen.Classification.generateRoute("id" to item2.id))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}