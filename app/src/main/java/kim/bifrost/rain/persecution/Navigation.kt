package kim.bifrost.rain.persecution

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kim.bifrost.rain.persecution.ui.MainScreen
import kim.bifrost.rain.persecution.ui.MainScreenScaffold
import kim.bifrost.rain.persecution.ui.Screen
import kim.bifrost.rain.persecution.ui.classification.ClassificationScreen
import kim.bifrost.rain.persecution.ui.classification.SingleClassificationScreen
import kim.bifrost.rain.persecution.ui.image.SingleImageScreen
import kim.bifrost.rain.persecution.ui.search.SearchScreen
import kim.bifrost.rain.persecution.ui.square.SquareScreen

/**
 * kim.bifrost.rain.persecution.Navigation
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/13 0:48
 **/
@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main/square") {
        // 单个图片页面
        composable(Screen.Image.route) {
            SingleImageScreen(navController, id = it.arguments?.getString("id")?.toIntOrNull() ?: error("wrong path"))
        }
        // 单个分类页面
        composable(Screen.Classification.route) {
            SingleClassificationScreen(navController, id = it.arguments?.getString("id")?.toIntOrNull() ?: error("wrong path"))
        }
        // 广场
        composable(Screen.Square.route) {
            MainScreenScaffold(navController = navController, screen = MainScreen.Square) {
                SquareScreen(navController = navController)
            }
        }
        // 分类列表
        composable(Screen.ClassificationList.route) {
            MainScreenScaffold(navController = navController, screen = MainScreen.Classification) {
                ClassificationScreen(navController)
            }
        }
        // 搜索
        composable(Screen.Query.route) {
            MainScreenScaffold(navController = navController, screen = MainScreen.Query) {
                SearchScreen(navController)
            }
        }
    }
}