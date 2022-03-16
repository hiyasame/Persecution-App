package kim.bifrost.rain.persecution.ui

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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kim.bifrost.rain.persecution.ComposeNavigation
import kim.bifrost.rain.persecution.R
import kim.bifrost.rain.persecution.ui.classification.ClassificationScreen
import kim.bifrost.rain.persecution.ui.search.SearchScreen
import kim.bifrost.rain.persecution.ui.square.SquareScreen
import kim.bifrost.rain.persecution.ui.theme.PersecutionTheme
import kim.bifrost.rain.persecution.utils.transparentStatusBar

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        transparentStatusBar()
        setContent {
            PersecutionTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    ComposeNavigation()
                }
            }
        }
    }
}

@Composable
fun Main(navController: NavController) {
    // 当前界面
    var screen: MainScreen by remember { mutableStateOf(MainScreen.Square) }
    val bottomItems = arrayOf("广场" to MainScreen.Square, "分类" to MainScreen.Classification, "搜索" to MainScreen.Query)
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
//                                3 -> Icon(
//                                    painterResource(id = R.drawable.ic_upload),
//                                    modifier = Modifier.size(20.dp),
//                                    contentDescription = null
//                                )
                            }
                        },
                        label = { Text(text = name) }
                    )
                }
            }
        }
    ) {
        when (screen) {
            MainScreen.Square -> {
                SquareScreen(navController)
            }
            MainScreen.Classification -> {
                ClassificationScreen(navController)
            }
            else -> {
                SearchScreen(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PersecutionTheme {
        Main(rememberNavController())
    }
}