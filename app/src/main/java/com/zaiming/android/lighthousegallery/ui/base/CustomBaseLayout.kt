package com.zaiming.android.lighthousegallery.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.core.view.isGone

/**
 * custom base viewGroup
 */
abstract class CustomBaseLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, deStyleAttr: Int = 0
): ViewGroup(context, attrs, deStyleAttr) {

    /**
     * 多个View进行测量
     */
    protected fun forEachAutoMeasure() {
        forEach { it.autoMeasure() }
    }

    protected fun View.autoMeasure() {
        if (isGone) return
        measure(
            defaultWidthMeasureSpec(this@CustomBaseLayout),
            defaultHeightMeasureSpec(this@CustomBaseLayout)
        )
    }

    /**
     * 布局
     * @param fromEnd: from right to left
     * @param fromBottom: from bottom to top
     */
    protected inline fun View.layout(
        x: Int, y: Int,
        fromEnd: Boolean = false,
        fromBottom: Boolean = false
    ) {
        if (isGone) return
        layout(
            if (fromEnd) this@CustomBaseLayout.measuredWidth - x - measuredWidth else x,
            if (fromBottom) this@CustomBaseLayout.measuredHeight - y - measuredHeight else y
        )
    }

    protected fun View.layout(x: Int, y: Int) = layout(
        x, y, x + measuredWidth, y + measuredHeight
    )

    protected fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int = when(layoutParams.width) {
        LayoutParams.MATCH_PARENT -> (parentView.measuredWidth - paddingStart - paddingEnd).toExactlyMeasureSpec()
        LayoutParams.WRAP_CONTENT -> Int.MAX_VALUE.toAtMostMeasureSpec()
        0 -> throw IllegalAccessException("Need special treatment for $this")
        else -> layoutParams.width.toExactlyMeasureSpec()
    }

    protected fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int = when(layoutParams.height) {
        LayoutParams.MATCH_PARENT -> (parentView.measuredHeight - paddingTop - paddingBottom).toExactlyMeasureSpec()
        LayoutParams.WRAP_CONTENT -> Int.MAX_VALUE.toAtMostMeasureSpec()
        0 -> throw IllegalAccessException("Need special treatment for $this")
        else -> layoutParams.height.toExactlyMeasureSpec()
    }

    protected fun Int.toExactlyMeasureSpec(): Int = MeasureSpec.makeMeasureSpec(this, MeasureSpec.EXACTLY)

    protected fun Int.toAtMostMeasureSpec(): Int = MeasureSpec.makeMeasureSpec(this, MeasureSpec.AT_MOST)
}