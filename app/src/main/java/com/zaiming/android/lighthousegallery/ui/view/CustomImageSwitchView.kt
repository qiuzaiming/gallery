package com.zaiming.android.lighthousegallery.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageSwitcher
import android.widget.ViewSwitcher
import androidx.appcompat.widget.AppCompatImageView
import com.zaiming.android.lighthousegallery.R

/**
 * image switcher
 */
class CustomImageSwitchView(context: Context, attributeSet: AttributeSet? = null) : ImageSwitcher(context, attributeSet), ViewSwitcher.ViewFactory {

    init {
        setFactory(this)
        setInAnimation(context, R.anim.anim_switch_in)
        setOutAnimation(context, R.anim.anim_switch_out)
    }

    override fun makeView(): View {
        return AppCompatImageView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }
}
