package com.zaiming.android.gallery.ui.viewgroup

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginStart
import com.zaiming.android.gallery.extensions.getString
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.wrapContent

class AndroidVersionLabelView(context: Context) : CustomLayout(context) {

    private val icon = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(24.dp, 24.dp)
        scaleType = ImageView.ScaleType.CENTER_CROP
        addView(this)
    }

    private val text = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent).also {
            it.marginStart = 8.dp
        }
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        setTextColor(Color.BLACK)
        addView(this)
    }

    fun setAndroidVersionLabelContent(@DrawableRes resId: Int, @StringRes stringId: Int) {
        icon.setImageResource(resId)
        text.text = stringId.getString(context)
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        /**
         * why View: text use autoMeasure?  View: text adapter matchParent wrapContent / exact measure standard.
         *
         * not use below error code
         *
         * val remainWidth = measuredWidth - icon.measuredWidth - text.marginStart
         * androidVersionText.measure(remainWidth.toExactlyMeasureSpec(), text.defaultHeightMeasureSpec(this))
         *
         */
        icon.autoMeasure()
        text.autoMeasure()
        setMeasuredDimension(measuredWidth, icon.measuredHeight.coerceAtLeast(text.measuredHeight))
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        val totalWidth = icon.measuredWidth + text.measureWidthWithMargin
        icon.layout((measuredWidth - totalWidth) / 2, icon.toVerticalCenter())
        text.layout(icon.right + text.marginStart, text.toVerticalCenter())
    }

}