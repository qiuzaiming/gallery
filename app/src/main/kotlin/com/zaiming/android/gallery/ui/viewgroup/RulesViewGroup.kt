package com.zaiming.android.gallery.ui.viewgroup

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.extensions.dp
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.matchParent
import com.zaiming.android.gallery.ui.base.wrapContent

class RulesMaterialViewGroup(context: Context) :
    MaterialCardView(context, null, R.style.SnapOutlinedStyle) {

    private val rules = RulesViewGroup(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent)
    }

    init {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            with(context) {
                it.topMargin = 20.dp.toInt()
                it.marginStart = 4.dp.toInt()
                it.marginEnd = 4.dp.toInt()
            }
        }
        with(context) {
            radius = 8.dp
            cardElevation = 0.dp
        }
        setCardBackgroundColor(R.color.teal_200)
        addView(rules)
    }
}

class RulesViewGroup(context: Context) : CustomLayout(context) {

    private val header = AppInfoHeadView(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            it.topMargin = 16.dp
        }
        setTitle(R.string.setting_privacy)
        this@RulesViewGroup.addView(this)
    }

    private val content = RulesContentViewGroup(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            it.topMargin = 30.dp
        }
        this@RulesViewGroup.addView(this)
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        header.autoMeasure()
        content.autoMeasure()
        setMeasuredDimension(measuredWidth,
            header.measureHeightWithMargin + content.measureHeightWithMargin)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        header.layout(0, header.marginTop)
        content.layout(0, header.bottom + content.marginTop)
    }

}

class RulesContentViewGroup(context: Context) : CustomLayout(context) {

    private val local = RulesItemViewGroup(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent)
        setContent("25", "Local")
        this@RulesContentViewGroup.addView(this)
    }

    private val arrow = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(48.dp, 48.dp)
        setImageResource(R.drawable.ic_arrow_right)
        this@RulesContentViewGroup.addView(this)
    }

    private val cloud = RulesItemViewGroup(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent)
        setContent("28", "Cloud")
        this@RulesContentViewGroup.addView(this)
    }

    private val btn = MaterialButton(context).apply {
        layoutParams = LayoutParams(matchParent, wrapContent).also {
            it.topMargin = 16.dp
            it.marginStart = 40.dp
            it.marginEnd = 40.dp
        }
        text = "Update"
        isEnabled = false
        this@RulesContentViewGroup.addView(this)
    }

    init {
        setPadding(0, 48.dp, 0, 48.dp)
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        local.autoMeasure()
        arrow.autoMeasure()
        cloud.autoMeasure()
        btn.measure((measuredWidth - btn.marginStart - btn.marginEnd).toExactlyMeasureSpec(),
            btn.defaultHeightMeasureSpec(this))
        setMeasuredDimension(measuredWidth,
            paddingTop + local.measuredHeight + btn.measureHeightWithMargin + paddingBottom)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        arrow.layout(arrow.toHorizontalCenter(this), paddingTop)
        local.layout(arrow.left - 24.dp - local.measuredWidth,
            local.toTargetViewVerticalCenter(arrow))
        cloud.layout(arrow.right + 24.dp, cloud.toTargetViewVerticalCenter(arrow))
        btn.layout(btn.toHorizontalCenter(this), local.bottom + btn.marginTop)
    }

}

class RulesItemViewGroup(context: Context) : CustomLayout(context) {

    private val title = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(wrapContent, wrapContent)
        setTextColor(Color.BLACK)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        isSingleLine = true
        ellipsize = TextUtils.TruncateAt.END
        addView(this)
    }

    private val dec = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(120.dp, wrapContent)
        setTextColor(Color.BLACK)
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        gravity = Gravity.CENTER
        addView(this)
    }

    fun setContent(titleContent: String, decContent: String) {
        title.text = titleContent
        dec.text = decContent
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        title.autoMeasure()
        dec.autoMeasure()
        setMeasuredDimension(title.measuredWidth.coerceAtLeast(dec.measuredWidth),
            title.measuredHeight + dec.measuredHeight)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        title.layout(title.toHorizontalCenter(this), 0)
        dec.layout(dec.toHorizontalCenter(this), title.bottom)
    }

}