package kim.bifrost.rain.persecution

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * kim.bifrost.rain.persecution.PersecutionApplication
 * Persecution
 *
 * @author 寒雨
 * @since 2022/3/15 16:04
 **/
class PersecutionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var app: Application
    }
}