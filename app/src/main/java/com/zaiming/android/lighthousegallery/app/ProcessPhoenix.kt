package com.zaiming.android.lighthousegallery.app

import android.content.Context
import android.content.Intent
import java.lang.IllegalStateException

/**
 * from drakeet
 */
object ProcessPhoenix {

    fun triggerRebirth(context: Context, intent: Intent? = null) {
        val restartIntent = intent ?: context.packageManager.getLaunchIntentForPackage(context.packageName)
            ?: throw IllegalStateException("No launcher intent for: ${context.packageName}")
        restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(restartIntent)
        Runtime.getRuntime().exit(0) // kill kill kill
    }
}
