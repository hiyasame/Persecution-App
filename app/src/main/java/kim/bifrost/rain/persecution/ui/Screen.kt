package kim.bifrost.rain.persecution.ui

/**
 * kim.bifrost.rain.persecution.ui.Screen
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/9 19:14
 **/
sealed class MainScreen(val route: String) {
    object Square : MainScreen("main/square")
    object Classification : MainScreen("main/classification")
    object Query : MainScreen("main/query")
}

sealed class Screen(val route: String) {
    fun generateRoute(vararg args: Pair<String, Any>): String {
        var str = route
        args.forEach { (s, a) ->
            str = str.replace("{$s}", a.toString())
        }
        return str
    }
    object Square : Screen("main/square")
    object ClassificationList : Screen("main/classification")
    object Query : Screen("main/query")
    object Image : Screen("image/{id}")
    object Classification : Screen("classification/{id}")
}