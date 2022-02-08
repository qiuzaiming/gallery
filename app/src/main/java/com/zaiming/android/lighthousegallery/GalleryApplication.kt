package com.zaiming.android.lighthousegallery

import android.app.Application

/**
 * @author zaiming
 */
class GalleryApplication : Application() {

    companion object {
        lateinit var instance: Application
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}