package com.zaiming.android.lighthousegallery

import android.app.Application
import com.zaiming.android.lighthousegallery.utils.captureException.CaptureOnlineException
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


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

        initTimber()

        initCaptureOnlineException()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initCaptureOnlineException() {
        CaptureOnlineException.init(this)
    }

}