package kim.bifrost.rain.persecution.ui.classification

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberImagePainter
import coil.size.Scale
import kim.bifrost.rain.persecution.R
import kim.bifrost.rain.persecution.logic.ClassificationViewModel
import kim.bifrost.rain.persecution.model.bean.ClassificationData
import kim.bifrost.rain.persecution.ui.theme.gray
import kim.bifrost.rain.persecution.ui.theme.titleTextStyle
import kim.bifrost.rain.persecution.ui.widgets.ImageCard
import kim.bifrost.rain.persecution.ui.widgets.SwipePagingList
import kim.bifrost.rain.persecution.utils.getOrNull

/**
 * kim.bifrost.rain.persecution.ui.classification.ClassificationScreen
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/11 17:49
 **/
@Preview
@Composable
fun ClassificationScreen() {
    val viewModel = viewModel<ClassificationViewModel>()
    val items = viewModel.pagingData.collectAsLazyPagingItems()
    SwipePagingList(
        items = items,
        header = {
            Text(
                text = "分类",
                style = titleTextStyle,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        },
        content = {
            items(items.itemCount / 2 + 1) { s ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val item = items.getOrNull(s * 2)
                    val item2 = items.getOrNull(s * 2 + 1)
                    item?.let {
                        ClassificationItem(
                            modifier = Modifier.weight(1f),
                            data = item
                        )
                    }
                    item2?.let {
                        ClassificationItem(
                            modifier = Modifier.weight(1f),
                            data = item2
                        )
                    }
                    if (item != null && item2 == null) {
                        Spacer(modifier = Modifier.weight(1.0f))
                    }
                }
            }
        }
    )
}

@Composable
fun ClassificationItem(
    modifier: Modifier = Modifier,
    data: ClassificationData = ClassificationData(
        2,
        "寒雨",
        "https://gitee.com/coldrain-moro/images_bed/raw/master/images/chino.jpg",
        "重邮の摆烂王"
    ),
    onClick: () -> Unit = {}
) {
    Box(
        Modifier.pointerInput(Unit) {
            detectTapGestures(
                onTap = {
                    onClick()
                }
            )
        }
    ) {
        Card(
            modifier = modifier
                .padding(10.dp)
                .width(140.dp)
                .height(75.dp)
                .align(Alignment.BottomCenter)
                .background(gray),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(Modifier.align(Alignment.Center)) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .widthIn(max = 130.dp),
                    text = data.name,
                    style = TextStyle(
                        fontSize = 18.sp
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .widthIn(max = 130.dp),
                    text = data.description,
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Gray
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(bottom = 70.dp)
                .size(130.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .align(Alignment.TopCenter),
                painter = rememberImagePainter(
                    data.avatar,
                    builder = {
                        placeholder(R.drawable.loading)
                        crossfade(true)
                        scale(Scale.FIT)
                    }
                ),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
fun Test() {
    Box(modifier = Modifier.fillMaxSize()) {
        ClassificationItem()
    }
}