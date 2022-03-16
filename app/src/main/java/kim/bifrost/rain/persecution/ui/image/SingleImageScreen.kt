package kim.bifrost.rain.persecution.ui.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.size.Scale
import kim.bifrost.rain.persecution.R
import kim.bifrost.rain.persecution.model.bean.SingleImageData
import kotlinx.coroutines.launch

/**
 * kim.bifrost.rain.persecution.ui.image.SingleImageScreen
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/13 15:59
 **/
@Composable
fun SingleImageScreen(navController: NavController = rememberNavController(), id: Int = 1) {
    val viewModel = viewModel<ImageViewModel>()
    val def = SingleImageData(id, 0, "")
    val image by viewModel.getImageData(id).collectAsState(initial = def)
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = Color.Black,
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                actions = {  },
                title = {  }
            )
        },
        floatingActionButton = {
            // 下载按钮
            FloatingActionButton(
                onClick = {
                     if (image != def) {
                         viewModel.savePic(image.image) {
                             coroutineScope.launch {
                                 scaffoldState.snackbarHostState.showSnackbar("已保存")
                             }
                         }
                     }
                },
                backgroundColor = Color.White,
                contentColor = Color.Gray
            ) {
                Icon(Icons.Filled.ArrowDropDown, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        snackbarHost = {
            SnackbarHost(hostState = it)
        }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            painter = rememberImagePainter(image.image) {
                placeholder(R.drawable.loading)
                crossfade(true)
                scale(Scale.FIT)
            },
            contentDescription = null
        )
    }
}