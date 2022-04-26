package com.zaiming.android.gallery.extensions

import android.view.View
import androidx.annotation.IntRange
import androidx.recyclerview.widget.RecyclerView
import com.zaiming.android.gallery.utils.AntiShakeUtils
import com.zaiming.android.gallery.utils.AntiShakeUtils.MAX_INTERVAL_TIME

fun View.beGone() {
    visibility = View.GONE
}

fun View.beVisible() {
    visibility = View.VISIBLE
}

inline fun View.setOnSingleClick(
    @IntRange(from = 0) internalTime: Long = MAX_INTERVAL_TIME,
    crossinline block: (view: View) -> Unit,
) {
    setOnClickListener {
        if (AntiShakeUtils.isInvalidClick(it, internalTime)) {
            return@setOnClickListener
        }
        block(this)
    }
}

fun RecyclerView.scrollToTopIfNeed() {
    if (canScrollVertically(-1)) {
        smoothScrollToPosition(0)
    }
}