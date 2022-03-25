package com.zaiming.android.lighthousegallery.ui.view

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.TextSwitcher
import android.widget.ViewSwitcher
import androidx.appcompat.widget.AppCompatTextView
import com.zaiming.android.lighthousegallery.R

/**
 * text switcher
 */
class TextSwitchView(context: Context, attributeSet: AttributeSet? = null): TextSwitcher(context, attributeSet), ViewSwitcher.ViewFactory {

    init {
        setFactory(this)
        setInAnimation(context, R.anim.anim_switch_in)
        setOutAnimation(context, R.anim.anim_switch_out)
    }

    override fun makeView(): View {
        return AppCompatTextView(context).apply {
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.CENTER
            setTypeface(null, Typeface.BOLD)
            setTextColor(Color.BLACK)
        }
    }
}