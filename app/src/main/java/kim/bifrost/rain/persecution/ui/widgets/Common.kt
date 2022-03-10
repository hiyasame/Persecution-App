package kim.bifrost.rain.persecution.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.size.Size
import kim.bifrost.rain.persecution.R

/**
 * kim.bifrost.rain.persecution.ui.widgets.Common
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/10 0:34
 **/
@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: String = "https://gitee.com/coldrain-moro/images_bed/raw/master/images/QQ%E6%88%AA%E5%9B%BE20211202011010.png",
    classification: String = "寒雨",
    imageId: Int = 0,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .wrapContentHeight()
            .padding(10.dp)
            .clickable(role = Role.Image, onClick = onClick),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
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
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(text = classification)
            }
        }
    }
}

@Composable
@Preview
fun Test() {
    Row(modifier = Modifier.fillMaxSize()) {
        ImageCard(modifier = Modifier.fillMaxWidth(0.5f))
        ImageCard(modifier = Modifier.fillMaxWidth())
    }
}