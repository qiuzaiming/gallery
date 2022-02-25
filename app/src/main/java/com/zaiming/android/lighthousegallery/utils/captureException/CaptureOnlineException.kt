package com.zaiming.android.lighthousegallery.utils.captureException

import android.app.Application
import android.content.pm.PackageManager
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.crashes.CrashesListener
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog
import com.microsoft.appcenter.crashes.model.ErrorReport
import com.zaiming.android.lighthousegallery.BuildConfig
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader


/**
 * @author zaiming
 */
object CaptureOnlineException {

    private val logBuilder = StringBuilder()

    fun init(application: Application) {

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        with(application) {
            AppCenter.start(
                this,
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA).metaData.getString("secret_appcenter"),
                Analytics::class.java, Crashes::class.java
            )
        }

        Crashes.setListener(object : CrashesListener {
            override fun shouldProcess(report: ErrorReport?): Boolean = true

            override fun shouldAwaitUserConfirmation(): Boolean = false

            override fun getErrorAttachments(report: ErrorReport?): MutableIterable<ErrorAttachmentLog> {

                report ?: return mutableListOf()

                runCatching {
                    val readLogCommand = arrayListOf("logcat", "-d")
                    val clearLogCommand = arrayListOf("logcat", "-c")

                    //capture log
                    val process = Runtime.getRuntime().exec(readLogCommand.toTypedArray())
                    val bufferReader = BufferedReader(InputStreamReader(process.inputStream))
                    var temporaryContent: String? = null
                    while (bufferReader.readLine().also { temporaryContent = it } != null) {
                        //clear log
                        Runtime.getRuntime().exec(clearLogCommand.toTypedArray())
                        logBuilder.appendLine(temporaryContent)
                    }
                }

                val logText = if (logBuilder.isNotBlank()) ErrorAttachmentLog.attachmentWithText(logBuilder.toString(), "log.txt") else return mutableListOf()

                return mutableListOf(logText)
            }

            override fun onBeforeSending(report: ErrorReport?) = Unit

            override fun onSendingFailed(report: ErrorReport?, e: Exception?) = Unit

            override fun onSendingSucceeded(report: ErrorReport?) = Unit

        })
    }

}