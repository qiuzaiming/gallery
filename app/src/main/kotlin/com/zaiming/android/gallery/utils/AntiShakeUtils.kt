package com.zaiming.android.gallery.utils

import android.view.View
import androidx.annotation.IntRange
import com.zaiming.android.gallery.R

/**
 * from libChecker
 */
object AntiShakeUtils {

    const val MAX_INTERVAL_TIME = 500L

    fun isInvalidClick(
        targetView: View,
        @IntRange(from = 0) internalTime: Long = MAX_INTERVAL_TIME,
    ): Boolean {
        val currentTime = System.currentTimeMillis()
        val viewTagValue = targetView.getTag(R.id.target_view_last_click_time) as? Long
        if (viewTagValue == null) {
            targetView.setTag(R.id.target_view_last_click_time, currentTime)
            return false
        }

        val isValidClick = currentTime - viewTagValue < internalTime
        if (!isValidClick) {
            targetView.setTag(R.id.target_view_last_click_time, currentTime)
        }
        return isValidClick
    }
}