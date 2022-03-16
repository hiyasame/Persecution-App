package kim.bifrost.rain.persecution.ui.image

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kim.bifrost.rain.persecution.PersecutionApplication
import kim.bifrost.rain.persecution.model.ApiService
import kim.bifrost.rain.persecution.utils.toast
import kim.bifrost.rain.persecution.utils.toastConcurrent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * kim.bifrost.rain.persecution.ui.image.ImageViewModel
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/15 15:11
 **/
class ImageViewModel : ViewModel() {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .build()

    fun getImageData(id: Int) = flow {
        emit(ApiService.getImage(id))
    }.flowOn(Dispatchers.IO)
        .map { it.data }

    fun savePic(url: String, onComplete: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = getImage(url)
            val resolver = PersecutionApplication.app.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "Persecution_${System.currentTimeMillis()}")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            }
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                resolver.openOutputStream(uri).use {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
                    onComplete()
                }
            }
        }
    }

    private suspend fun getImage(url: String): Bitmap = suspendCancellableCoroutine {
        client.newCall(
            Request.Builder()
                .url(url)
                .build()
        ).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                it.resumeWithException(e)
            }

            override fun onResponse(call: Call, response: Response) {
                val data = response.body!!.bytes()
                val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
                it.resumeWith(Result.success(bitmap))
            }
        })
    }
}