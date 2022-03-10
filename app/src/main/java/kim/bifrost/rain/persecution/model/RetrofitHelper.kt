package kim.bifrost.rain.persecution.model

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

/**
 * kim.bifrost.rain.persecution.model.RetrofitHelper
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/9 9:16
 **/
object RetrofitHelper {

    private const val BASE_URL = "http://42.192.196.215:8080"
    private val retrofit by lazy { initRetrofit() }
    val service: ApiService by lazy { retrofit.create(ApiService::class.java) }

    private fun initRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .build()
    }

    private fun getClient(): OkHttpClient {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            @SuppressLint("CustomX509TrustManager")
            object : X509TrustManager {
                @SuppressLint("TrustAllX509TrustManager")
                override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        return OkHttpClient.Builder()
//            .sslSocketFactory(
//                SSLContext.getInstance("SSL").run {
//                    init(null, trustAllCerts, SecureRandom())
//                    socketFactory
//                }
//            )
//            .hostnameVerifier { _, _ -> true }
            .build()
    }
}