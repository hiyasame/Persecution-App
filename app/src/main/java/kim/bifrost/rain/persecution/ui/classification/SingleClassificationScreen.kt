package kim.bifrost.rain.persecution.ui.classification

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.placeholder.material.placeholder
import kim.bifrost.rain.persecution.model.bean.ClassificationData
import kim.bifrost.rain.persecution.ui.Screen
import kim.bifrost.rain.persecution.ui.widgets.CardImage
import kim.bifrost.rain.persecution.ui.widgets.ImageCard
import kim.bifrost.rain.persecution.ui.widgets.SwipePagingList
import kim.bifrost.rain.persecution.utils.getOrNull
import kotlinx.coroutines.launch

/**
 * kim.bifrost.rain.persecution.ui.classification.SingleClassificationScreen
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/13 15:56
 **/
@Composable
fun SingleClassificationScreen(navController: NavController = rememberNavController(), id: Int) {
    val viewModel = viewModel() { SingleClassificationViewModel(id) }
    val def = ClassificationData(0, "Loading", "", "")
    val infoState by viewModel.classificationFlow.collectAsState(def)
    val images = viewModel.dataFlow.collectAsLazyPagingItems()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    // 上传图片
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.OpenDocument()) { uri ->
        coroutineScope.launch {
            if (uri == null) {
                scaffoldState.snackbarHostState.showSnackbar("图片解析失败")
                return@launch
            }
            val url = viewModel.upload(uri)
            val res = viewModel.uploadToClassification(url)
            if (res.errorCode != 0) {
                scaffoldState.snackbarHostState.showSnackbar(res.message)
            } else {
                scaffoldState.snackbarHostState.showSnackbar("上传成功")
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                contentColor = Color.Gray,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {  },
                title = {
                    Row {
                        Image(
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(36.dp),
                            painter = rememberImagePainter(infoState.avatar) {
                                transformations(CircleCropTransformation())
                            },
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Text(text = infoState.name, modifier = Modifier.align(Alignment.CenterVertically))
                    }
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { launcher.launch(arrayOf("image/*")) },
                backgroundColor = Color.White,
                contentColor = Color.Gray
            ) {
                Icon(Icons.Filled.KeyboardArrowUp, contentDescription = null)
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = it)
        }
    ) {
        SwipePagingList(items = images) { sw ->
            items(images.itemCount / 2 + 1) { s ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val item = images.getOrNull(s * 2)
                    val item2 = images.getOrNull(s * 2 + 1)
                    item?.let {
                        CardImage(
                            modifier = Modifier
                                .weight(1.0f)
                                .placeholder(sw.isRefreshing),
                            image = it.image,
                            onClick = {
                                navController.navigate(Screen.Image.generateRoute("id" to it.id))
                            },
                        )
                    }
                    item2?.let {
                        CardImage(
                            modifier = Modifier
                                .weight(1.0f)
                                .placeholder(sw.isRefreshing),
                            image = it.image,
                            onClick = {
                                navController.navigate(Screen.Image.generateRoute("id" to it.id))
                            },
                        )
                    }
                    if (item2 == null && item != null) {
                        Spacer(modifier = Modifier.weight(1.0f))
                    }
                }
            }
        }
    }
}