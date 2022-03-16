package kim.bifrost.rain.persecution.ui.classification

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.core.graphics.drawable.toIcon
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kim.bifrost.rain.persecution.PersecutionApplication
import kim.bifrost.rain.persecution.model.ApiService
import kim.bifrost.rain.persecution.model.BasePagingSource
import kim.bifrost.rain.persecution.model.bean.BaseResponse
import kim.bifrost.rain.persecution.utils.getFilePathFromContentUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * kim.bifrost.rain.persecution.ui.classification.SingleClassificationViewModel
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/16 11:05
 **/
class SingleClassificationViewModel(private val id: Int) : ViewModel() {

    val dataFlow = Pager(
        config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
        pagingSourceFactory = {
            BasePagingSource {
                ApiService.getImages(id, offset = it * 20).data.data
            }
        }
    ).flow.cachedIn(viewModelScope)

    val classificationFlow = flow {
        emit(ApiService.getClassification(id).data)
    }.flowOn(Dispatchers.IO)

    suspend fun upload(uri: Uri): String = withContext(Dispatchers.IO) {
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

    suspend fun uploadToClassification(url: String): BaseResponse<String> = withContext(Dispatchers.IO) {
        ApiService.uploadToClassification(cid = id, image = url)
    }

    companion object {
        private val MEDIA_TYPE_JPEG: MediaType = "image/jpeg".toMediaType()
    }
}