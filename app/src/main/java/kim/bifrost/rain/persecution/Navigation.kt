package kim.bifrost.rain.persecution

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kim.bifrost.rain.persecution.ui.Main
import kim.bifrost.rain.persecution.ui.Screen
import kim.bifrost.rain.persecution.ui.classification.SingleClassificationScreen
import kim.bifrost.rain.persecution.ui.image.SingleImageScreen

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
    NavHost(navController = navController, startDestination = "main") {
        // 主页面
        composable(Screen.Main.route) {
            Main(navController)
        }
        // 单个图片页面
        composable(Screen.Image.route) {
            SingleImageScreen(navController, id = it.arguments?.getString("id")?.toIntOrNull() ?: error("wrong path"))
        }
        // 单个分类页面
        composable(Screen.Classification.route) {
            SingleClassificationScreen(navController, id = it.arguments?.getString("id")?.toIntOrNull() ?: error("wrong path"))
        }
    }
}