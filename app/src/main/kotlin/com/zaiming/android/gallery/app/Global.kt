package com.zaiming.android.gallery.app

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.MessageQueue
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
        if (stackTraceContent.contains("SavedStateProvider with the given key is already registered")) {
            // from google issue: https://github.com/google/dagger/issues/2328
            return
        }
        throw e
    }

    /**
     * from drakeet
     */
    fun doOnMainThreadIdle(action: () -> Unit) {

        val idleHandler = MessageQueue.IdleHandler {
            handler.removeCallbacksAndMessages(null)
            action()
            false
        }

        fun setupIdleHandler(queue: MessageQueue) {
            queue.addIdleHandler(idleHandler)
        }

        if (Looper.myLooper() == Looper.getMainLooper()) {
            setupIdleHandler(Looper.myQueue())
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setupIdleHandler(Looper.getMainLooper().queue)
            } else {
                handler.post { setupIdleHandler(Looper.myQueue()) }
            }
        }
    }
}
