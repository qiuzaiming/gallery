package com.zaiming.android.gallery.ui.base

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.forEach
import androidx.core.view.isGone
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop

const val matchParent = MATCH_PARENT
const val wrapContent = WRAP_CONTENT

/**
 * from drakeet
 */
abstract class CustomBaseLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    deStyleAttr: Int = 0
) : ViewGroup(context, attrs, deStyleAttr) {

    protected val Int.dp: Int
        get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()

    class LayoutParams(width: Int, height: Int) : MarginLayoutParams(width, height)

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(matchParent, wrapContent)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        this.onMeasureChildren(widthMeasureSpec, heightMeasureSpec)
    }

    /**
     * 提示用于需要重写onMeasure方法进行测量子View
     */
    protected abstract fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int)

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
    protected fun View.layout(
        x: Int,
        y: Int,
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

    protected fun View.toHorizontalCenter(): Int {
        return (this@CustomBaseLayout.measuredWidth - measuredWidth) / 2
    }

    protected fun View.toVerticalCenter(): Int {
        return (this@CustomBaseLayout.measuredHeight - measuredHeight) / 2
    }

    protected val View.measureWidthWithMargin get() = (measuredWidth + marginStart + marginEnd)
    protected val View.measureHeightWithMargin get() = (measuredHeight + marginTop + marginBottom)

    protected fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int = when (layoutParams.width) {
        matchParent -> (parentView.measuredWidth - paddingStart - paddingEnd).toExactlyMeasureSpec()
        wrapContent -> Int.MAX_VALUE.toAtMostMeasureSpec()
        0 -> throw IllegalAccessException("Need special treatment for $this")
        else -> layoutParams.width.toExactlyMeasureSpec()
    }

    protected fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int = when (layoutParams.height) {
        matchParent -> (parentView.measuredHeight - paddingTop - paddingBottom).toExactlyMeasureSpec()
        wrapContent -> Int.MAX_VALUE.toAtMostMeasureSpec()
        0 -> throw IllegalAccessException("Need special treatment for $this")
        else -> layoutParams.height.toExactlyMeasureSpec()
    }

    protected fun Int.toExactlyMeasureSpec(): Int = MeasureSpec.makeMeasureSpec(this, MeasureSpec.EXACTLY)

    protected fun Int.toAtMostMeasureSpec(): Int = MeasureSpec.makeMeasureSpec(this, MeasureSpec.AT_MOST)

    fun addView(child: View, width: Int, height: Int, apply: (LayoutParams.() -> Unit) = {}) {
        val params = generateDefaultLayoutParams()
        params.width = width
        params.height = height
        apply(params)
        super.addView(child, params)
    }
}
