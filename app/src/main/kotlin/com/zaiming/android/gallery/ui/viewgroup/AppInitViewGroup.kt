package com.zaiming.android.gallery.ui.viewgroup

import android.content.Context
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.zaiming.android.gallery.ui.base.CustomLayout
import com.zaiming.android.gallery.ui.base.wrapContent

class AppInitViewGroup(context: Context) : CustomLayout(context) {

    private val lottie = LottieAnimationView(context).apply {
        layoutParams = LayoutParams(200.dp, 200.dp)
        imageAssetsFolder = "/"
        setAnimation("loading.json")
        repeatCount = INFINITE
        playAnimation()
        addView(this)
    }

    private val linearProgressIndicator = LinearProgressIndicator(context).apply {
        layoutParams = LayoutParams(200.dp, wrapContent)
        trackCornerRadius = 4.dp
        addView(this)
    }

    override fun onMeasureChildren(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        lottie.autoMeasure()
        linearProgressIndicator.autoMeasure()
        setMeasuredDimension(measuredWidth,
            lottie.measuredHeight + linearProgressIndicator.measuredHeight)
    }

    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {
        lottie.layout(lottie.toHorizontalCenter(this), 0)
        linearProgressIndicator.layout(linearProgressIndicator.toHorizontalCenter(this),
            lottie.bottom)
    }
}