package kim.bifrost.rain.persecution.utils

import android.app.Activity
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.paging.compose.LazyPagingItems
import kim.bifrost.rain.persecution.PersecutionApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * kim.bifrost.rain.persecution.utils.Utils
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/11 22:56
 **/
fun <T : Any> LazyPagingItems<T>.getOrNull(slot: Int): T? {
    if (slot >= itemCount) return null
    return get(slot)
}

fun String.toast(time: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(PersecutionApplication.app, this, time).show()
}

suspend fun String.toastConcurrent(time: Int = Toast.LENGTH_SHORT) = withContext(Dispatchers.Main) {
    Toast.makeText(PersecutionApplication.app, this@toastConcurrent, time).show()
}


fun Activity.transparentStatusBar() {
    transparentStatusBar(window)
}

private fun transparentStatusBar(window: Window) {
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = android.graphics.Color.TRANSPARENT
}

fun getFilePathFromContentUri(uri: Uri): String {
    val column = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = PersecutionApplication.app.contentResolver.query(uri, column, null, null, null)!!
    cursor.moveToFirst()
    val index = cursor.getColumnIndexOrThrow(column[0])
    val filePath = cursor.getString(index)
    cursor.close()
    return filePath
}