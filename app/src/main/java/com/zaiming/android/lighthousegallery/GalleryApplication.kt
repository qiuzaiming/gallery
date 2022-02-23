package com.zaiming.android.lighthousegallery

import android.app.Application
import android.content.pm.PackageManager
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
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

        initAppCenter()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initAppCenter() {
        AppCenter.start(
            this, packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData.getString("secret_appcenter"),
            Analytics::class.java, Crashes::class.java
        )
    }

}