package com.zaiming.android.lighthousegallery.app

import android.os.Handler
import android.os.Looper
import android.util.Log
import timber.log.Timber

object Global {

    private val handler = Handler(Looper.getMainLooper())

    fun start() {
        handler.post {
            while (true) {
                try {
                    Looper.loop()
                } catch (e: Throwable) {
                    handlerException(e)
                }
            }
        }
    }

    private fun handlerException(e: Throwable) {
        val stackTraceContent = Log.getStackTraceString(e)
        Timber.e(e)
        throw e
    }
}