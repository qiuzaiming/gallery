package com.zaiming.android.gallery

import android.app.Application
import android.content.Context
import com.zaiming.android.gallery.app.Global
import com.zaiming.android.gallery.utils.captureException.CaptureOnlineException
import dagger.hilt.android.HiltAndroidApp

/**
 * @author zaiming
 */
@HiltAndroidApp
class GalleryApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initCaptureOnlineException()
    }

    private fun initCaptureOnlineException() {
        CaptureOnlineException.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        Global.start()
    }
}
