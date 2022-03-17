package kim.bifrost.rain.persecution.ui.classification

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kim.bifrost.rain.persecution.PersecutionApplication
import kim.bifrost.rain.persecution.model.ApiService
import kim.bifrost.rain.persecution.model.BasePagingSource
import kim.bifrost.rain.persecution.model.bean.BaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * kim.bifrost.rain.persecution.ui.classification.ClassificationViewModel
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/11 21:52
 **/
class ClassificationViewModel : ViewModel() {

    val pagingData = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            BasePagingSource {
                ApiService.getAllClassification(offset = it * 20).data
            }
        }
    ).flow.cachedIn(viewModelScope)

    suspend fun createNewClassification(avatar: Uri, name: String, description: String): BaseResponse<String> = withContext(Dispatchers.IO) {
        val avatarUrl = upload(avatar)
        ApiService.createClassification(name, avatarUrl, description)
    }

    private suspend fun upload(uri: Uri): String = withContext(Dispatchers.IO) {
        val bitmap = BitmapFactory.decodeStream(PersecutionApplication.app.contentResolver.openInputStream(uri))
        val dir = PersecutionApplication.app.filesDir
        val file = File(dir, UUID.randomUUID().toString() + ".jpg")
        BufferedOutputStream(FileOutputStream(file)).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            it.flush()
        }
        val body = file.asRequestBody(MEDIA_TYPE_JPEG)
        val part = MultipartBody.Part.createFormData("image", file.name, body)
        ApiService.upload(part).data.also { file.delete() }
    }

    companion object {
        private val MEDIA_TYPE_JPEG: MediaType = "image/jpeg".toMediaType()
    }
}