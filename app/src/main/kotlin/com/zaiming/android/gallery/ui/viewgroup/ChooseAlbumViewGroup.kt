package com.zaiming.android.gallery.ui.viewgroup

import android.content.Context
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginStart
import com.google.android.material.card.MaterialCardView
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.extensions.dp
import com.zaiming.android.gallery.extensions.getString
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.matchParent
import com.zaiming.android.gallery.ui.base.wrapContent

class MaterialChooseAlbumViewGroup(context: Context) :
    MaterialCardView(context, null, R.style.SnapOutlinedStyle) {

    val chooseAlbumViewGroup = ChooseAlbumViewGroup(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent)
        with(context) {
            setPadding(20.dp.toInt(), 32.dp.toInt(), 20.dp.toInt(), 32.dp.toInt())
        }
    }

    init {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            with(context) {
                it.marginStart = 4.dp.toInt()
                it.marginEnd = 4.dp.toInt()
            }
        }
        with(context) {
            radius = 8.dp
            cardElevation = 0.dp
        }
        setCardBackgroundColor(R.color.teal_200)
        addView(chooseAlbumViewGroup)
    }
}


class ChooseAlbumViewGroup(context: Context) : CustomLayout(context) {

    private val icon = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(45.dp, 45.dp)
        scaleType = ImageView.ScaleType.CENTER_INSIDE
        setBackgroundResource(R.drawable.bg_circle_secondary_container)
        addView(this)
    }

    private val title = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent).also {
            it.marginStart = 20.dp
        }
        setTextColor(R.color.black)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        addView(this)
    }

    private val subTitle = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent)
        setTextColor(R.color.black)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 8f)
        addView(this)
    }

    fun setResource(@DrawableRes resId: Int, @StringRes titleId: Int, @StringRes contentId: Int) {
        icon.setImageResource(resId)
        title.text = titleId.getString(context)
        subTitle.text = contentId.getString(context)
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        icon.autoMeasure()
        val titleContentWidth =
            measuredWidth - icon.measuredWidth - paddingStart - paddingEnd - title.marginStart
        title.measure(titleContentWidth.toExactlyMeasureSpec(),
            title.defaultHeightMeasureSpec(this))
        subTitle.measure(titleContentWidth.toExactlyMeasureSpec(),
            subTitle.defaultHeightMeasureSpec(this))
        setMeasuredDimension(measuredWidth,
            icon.measuredHeight.coerceAtLeast(title.measuredHeight + subTitle.measuredHeight) + paddingTop + paddingBottom)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        icon.layout(paddingStart, icon.toVerticalCenter())
        title.layout(icon.right + title.marginStart,
            (measuredHeight - title.measuredHeight - subTitle.measuredHeight) / 2)
        subTitle.layout(title.left, title.bottom)
    }

}