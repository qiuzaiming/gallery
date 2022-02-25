package com.zaiming.android.lighthousegallery.utils.captureException

import android.app.Application
import android.content.pm.PackageManager
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.crashes.CrashesListener
import com.microsoft.appcenter.crashes.ingestion.models.ErrorAttachmentLog
import com.microsoft.appcenter.crashes.model.ErrorReport


/**
 * @author zaiming
 */
object CaptureOnlineException {

    private val logBuilder = StringBuilder()

    fun init(application: Application) {
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

                //read the specific file upload to server
                return mutableListOf()
            }

            override fun onBeforeSending(report: ErrorReport?) = Unit

            override fun onSendingFailed(report: ErrorReport?, e: Exception?) = Unit

            override fun onSendingSucceeded(report: ErrorReport?) = Unit

        })
    }

    fun log(message: String?) {
        if (message != null) {
            logBuilder.appendLine(message)
        }
    }

    fun flushLog() {

    }

}