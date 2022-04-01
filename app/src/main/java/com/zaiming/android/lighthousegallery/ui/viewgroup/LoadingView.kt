package com.zaiming.android.lighthousegallery.ui.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginTop
import com.zaiming.android.lighthousegallery.R
import com.zaiming.android.lighthousegallery.ui.base.CustomBaseLayout

/**
 * Loading ViewGroup
 */
class LoadingView(context: Context, attributeSet: AttributeSet) : CustomBaseLayout(context, attributeSet) {

    private val loadingImageView = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(160.dp, 160.dp)
        setImageResource(R.drawable.loading)
        addView(this)
    }

    private val loadingTextView = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(WRAP_CONTENT, WRAP_CONTENT).apply {
            topMargin = 10.dp
        }
        text = context.getString(R.string.text_loading)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        addView(this)
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        forEachAutoMeasure()
        setMeasuredDimension(
            loadingImageView.measuredWidth.coerceAtLeast(loadingTextView.measuredWidth),
            loadingImageView.measureHeightWithMargin + loadingTextView.measureHeightWithMargin
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        loadingImageView.layout(loadingImageView.toHorizontalCenter(), 0)
        loadingTextView.layout(loadingTextView.toHorizontalCenter(), loadingImageView.measureHeightWithMargin + loadingTextView.marginTop)
    }
}
