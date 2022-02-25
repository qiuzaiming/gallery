package com.zaiming.android.lighthousegallery.utils.captureException

import android.util.Log
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author zaiming
 */
class CrashReportTree : Timber.Tree() {

    private val logTimeFormat by lazy {
        SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS] [Z]", Locale.US)
    }

    private val date by lazy {
        Date()
    }

    override fun log(priority: Int, tag: String?, message: String, throwable: Throwable?) {
        val logLevel = when (priority) {
            Log.VERBOSE -> "v"
            Log.DEBUG -> "d"
            Log.INFO -> "i"
            Log.WARN -> "w"
            Log.ERROR -> "e"
            Log.ASSERT -> "a"
            else -> priority.toString()
        }

        val logTag = if (tag != null) "/$tag" else ""
        date.time = System.currentTimeMillis()
        CaptureOnlineException.log("${logTimeFormat.format(date)} $logLevel$logTag: $message")

        if (throwable != null) {
            CaptureOnlineException.log("Language: ${Locale.getDefault().country}-${Locale.getDefault().language}")
            CaptureOnlineException.log(throwable.message)
        }

        CaptureOnlineException.flushLog()
    }

}