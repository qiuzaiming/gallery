package com.zaiming.android.lighthousegallery

import android.app.Application
import com.zaiming.android.lighthousegallery.utils.captureException.CaptureOnlineException
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

}