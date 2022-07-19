package com.zaiming.android.gallery.ui.viewgroup

import android.content.Context
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginStart
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.matchParent
import com.zaiming.android.gallery.ui.base.wrapContent

class AppItemViewGroup(context: Context) : CustomLayout(context) {

    init {
        setPadding(6.dp, 2.dp, 6.dp, 2.dp)
    }

    private val icon = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(45.dp, 45.dp)
        addView(this)
    }

    private val name = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            it.marginStart = 8.dp
        }
        maxLines = 1
        ellipsize = TextUtils.TruncateAt.END
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        setTextColor(R.color.black)
        addView(this)
    }

    private val packageName = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent)
        ellipsize = TextUtils.TruncateAt.MARQUEE
        isSingleLine = true
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        setTextColor(R.color.black)
        addView(this)
    }

    private val versionCode = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent)
        maxLines = 1
        gravity = Gravity.START or Gravity.CENTER_VERTICAL
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        setTextColor(R.color.black)
        addView(this)
    }

    fun setContent(@DrawableRes resId: Int, packageName: String, name: String, versionCode: String) {
        icon.setImageResource(resId)
        this.name.text = name
        this.versionCode.text = versionCode
        this.packageName.apply {
            text = packageName
            isSelected = true
        }
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        icon.autoMeasure()
        val remainWidth = measuredWidth - paddingStart - paddingEnd - icon.measuredWidth - name.marginStart
        name.measure(remainWidth.toExactlyMeasureSpec(),
            name.defaultHeightMeasureSpec(this))
        packageName.measure(remainWidth.toExactlyMeasureSpec(),
            packageName.defaultHeightMeasureSpec(this))
        versionCode.measure(remainWidth.toExactlyMeasureSpec(),
            versionCode.defaultHeightMeasureSpec(this))

        setMeasuredDimension(measuredWidth,
            icon.measuredHeight.coerceAtLeast((name.measuredHeight + packageName.measuredHeight + versionCode.measuredHeight)) + paddingTop + paddingBottom)

    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        icon.layout(paddingStart, icon.toVerticalCenter(this))
        name.layout(paddingEnd, paddingTop, fromEnd = true)
        packageName.layout(paddingEnd, name.bottom, fromEnd = true)
        versionCode.layout(paddingEnd, packageName.bottom, fromEnd = true)
    }

}