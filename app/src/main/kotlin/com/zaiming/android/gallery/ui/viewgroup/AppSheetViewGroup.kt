package com.zaiming.android.gallery.ui.viewgroup

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.extensions.dp
import com.zaiming.android.gallery.extensions.getString
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.matchParent
import com.zaiming.android.gallery.ui.base.wrapContent

class AppInfoMaterialViewGroup(context: Context) : MaterialCardView(context, null, R.style.SnapOutlinedStyle) {

    private val appInfoViewGroup = AppInfoViewGroup(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent)
    }

    init {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            with(context) {
                it.marginStart = 4.dp.toInt()
                it.marginEnd = 4.dp.toInt()
                it.topMargin = 20.dp.toInt()
            }
        }
        with(context) {
            radius = 8.dp
            cardElevation = 0.dp
        }
        setCardBackgroundColor(R.color.teal_200)
        addView(appInfoViewGroup)
    }
}

class AppInfoViewGroup(context: Context) : CustomLayout(context) {

    init {
        setPadding(20.dp, 16.dp, 20.dp, 0)
    }

    private val headerView = AppInfoHeadView(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent)
        this@AppInfoViewGroup.addView(this)
    }

    private val launchItem = AppInfoContentItemViewGroup(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent).also {
            it.topMargin = 30.dp
        }
        setContent(R.drawable.ic_launch, R.string.lab_about)
        this@AppInfoViewGroup.addView(this)
    }

    private val settingItem = AppInfoContentItemViewGroup(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent)
        setContent(R.drawable.ic_settings, R.string.setting_privacy)
        this@AppInfoViewGroup.addView(this)
    }

    private val list = RecyclerView(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            it.topMargin = 4.dp
        }
        overScrollNever()
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        headerView.autoMeasure()
        val itemWidth = (measuredWidth - paddingStart - paddingEnd) / 4
        launchItem.measure(itemWidth.toExactlyMeasureSpec(),
            launchItem.defaultHeightMeasureSpec(this))
        settingItem.measure(itemWidth.toExactlyMeasureSpec(),
            settingItem.defaultHeightMeasureSpec(this))
        list.measure((measuredWidth - paddingStart - paddingEnd).toExactlyMeasureSpec(),
            list.defaultHeightMeasureSpec(this))
        setMeasuredDimension(measuredWidth,
            paddingTop + headerView.measuredHeight + launchItem.measuredHeight.coerceAtLeast(settingItem.measuredHeight) + launchItem.marginTop + list.measureHeightWithMargin + paddingBottom)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        headerView.layout(0, paddingTop)
        launchItem.layout(paddingStart, headerView.bottom + launchItem.marginTop)
        settingItem.layout(launchItem.right, launchItem.top)
        list.layout(paddingStart, launchItem.bottom + list.marginTop)
    }

}

class AppInfoContentItemViewGroup(context: Context) : CustomLayout(context) {

    init {
        setPadding(4.dp, 12.dp, 4.dp, 12.dp)
    }

    private val icon = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(45.dp, 45.dp)
        scaleType = ImageView.ScaleType.CENTER_CROP
        setBackgroundResource(R.drawable.bg_circle_secondary_container)
        addView(this)
    }

    private val text = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent).also {
            it.topMargin = 12.dp
        }
        setTextColor(Color.BLACK)
        gravity = Gravity.CENTER_HORIZONTAL
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        maxLines = 1
        addView(this)
    }

    fun setContent(@DrawableRes resId: Int, @StringRes stringId: Int) {
        icon.setImageResource(resId)
        text.text = stringId.getString(context)
    }


    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        icon.autoMeasure()
        text.measure((measuredWidth - paddingStart - paddingEnd).toExactlyMeasureSpec(), text.defaultHeightMeasureSpec(this))
        setMeasuredDimension(measuredWidth, paddingTop + icon.measuredHeight + text.measureHeightWithMargin + paddingBottom)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        icon.layout(icon.toHorizontalCenter(this), paddingTop)
        text.layout(text.toHorizontalCenter(this), icon.bottom + text.marginTop)
    }

}

/**
 * 头布局文件
 */
class AppInfoHeadView(context: Context) : CustomLayout(context) {

    private val view = View(context).apply {
        layoutParams = LayoutParams(40.dp, 4.dp).also {
            it.topMargin = 20.dp
        }
        setBackgroundColor(R.color.teal_200)
        addView(this)
    }

    private val title = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            it.topMargin = 20.dp
        }
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        setTextColor(R.color.black)
        text = "进一步操作"
        gravity = Gravity.CENTER_HORIZONTAL
        addView(this)
    }

    fun setTitle(@StringRes stringId: Int) {
        title.text = stringId.getString(context)
    }


    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        view.autoMeasure()
        title.autoMeasure()
        setMeasuredDimension(measuredWidth,
            view.measureHeightWithMargin + title.measureHeightWithMargin)

        view.apply {
            clipToOutline = true
            outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(p0: View?, outline: Outline?) {
                    outline?.setRoundRect(0,
                        0,
                        view.measuredWidth,
                        view.measuredHeight,
                        view.measuredHeight / 2f)
                }

            }
        }
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        view.layout(view.toHorizontalCenter(), 0)
        title.layout(title.toHorizontalCenter(), view.bottom + title.marginTop)
    }

}