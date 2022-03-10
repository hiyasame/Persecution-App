package kim.bifrost.rain.persecution

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kim.bifrost.rain.persecution.ui.Screen
import kim.bifrost.rain.persecution.ui.square.SquareScreen
import kim.bifrost.rain.persecution.ui.theme.PersecutionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PersecutionTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    Main()
                }
            }
        }
    }
}

@Composable
fun Main() {
    // 当前界面
    var screen: Screen by remember { mutableStateOf(Screen.Square) }
    val bottomItems = arrayOf("广场" to Screen.Square, "分类" to Screen.Classification, "搜索" to Screen.Query)
    Scaffold(
        bottomBar = {
            BottomNavigation(
                backgroundColor = Color.White,
                modifier = Modifier.height(50.dp)
            ) {
                bottomItems.forEachIndexed { index, (name, sc) ->
                    BottomNavigationItem(
                        selected = screen == sc,
                        onClick = { screen = sc },
                        icon = {
                            when(index){
                                0 -> Icon(Icons.Filled.Home, contentDescription = null)
                                1 -> Icon(painterResource(id = R.drawable.ic_classification), modifier = Modifier.size(20.dp), contentDescription = null)
                                2 -> Icon(Icons.Filled.Search, contentDescription = null)
                            }
                        },
                        label = { Text(text = name) }
                    )
                }
            }
        }
    ) {
        when (screen) {
            Screen.Square -> {
                SquareScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PersecutionTheme {
        Main()
    }
}