package com.zaiming.android.lighthousegallery.extensions

import android.graphics.Color
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.zaiming.android.lighthousegallery.utils.windowInsets.EdgeInsetDelegate


internal fun getContentTransform(): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        addTarget(android.R.id.content)
        duration = 450L
        pathMotion = MaterialArcMotion()
        isElevationShadowEnabled = true
        startElevation = 9f
        endElevation = 9f
        interpolator = AccelerateDecelerateInterpolator()
        startContainerColor = Color.WHITE
        endContainerColor = Color.WHITE
    }
}

fun AppCompatActivity.applyExitMaterialTransform() {
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementsUseOverlay = false
}

fun AppCompatActivity.applyMaterialTransform(transitionName: String?) {
    window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
    ViewCompat.setTransitionName(findViewById(android.R.id.content), transitionName)

    setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
    window.sharedElementEnterTransition = getContentTransform()
    window.sharedElementReturnTransition = getContentTransform()
}

fun AppCompatActivity.applyImmersionWithWindowInsets() {
    window.statusBarColor = Color.TRANSPARENT
    ViewCompat.getWindowInsetsController(findViewById(android.R.id.content))?.apply {
        // change statusBar text to black color.
        isAppearanceLightStatusBars = true
    }
    EdgeInsetDelegate(this).start()
}