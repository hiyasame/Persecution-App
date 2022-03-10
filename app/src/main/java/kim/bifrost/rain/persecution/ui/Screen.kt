package kim.bifrost.rain.persecution.ui

/**
 * kim.bifrost.rain.persecution.ui.Screen
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/9 19:14
 **/
sealed class Screen(val route: String) {
    object Square : Screen("square")
    object Classification : Screen("classification")
    object Query : Screen("query")
}