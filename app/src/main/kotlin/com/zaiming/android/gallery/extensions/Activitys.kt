@file:kotlin.jvm.JvmName("ActivityKts")

package com.zaiming.android.gallery.extensions

import android.graphics.Color
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.zaiming.android.gallery.utils.windowInsets.EdgeInsetDelegate
import java.lang.reflect.ParameterizedType

internal fun getContentTransform(): MaterialContainerTransform {
    return MaterialContainerTransform().apply {
        addTarget(android.R.id.content)
        duration = 450L
        pathMotion = MaterialArcMotion()
        isElevationShadowEnabled = true
        startElevation = 9f
        endElevation = 9f
        interpolator = AccelerateDecelerateInterpolator()
        startContainerColor = Color.TRANSPARENT
        endContainerColor = Color.TRANSPARENT
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

/**
 * two view apply motion animator
 */
fun applyMaterialContainerTransitionBetweenTwoViews(rootView: ViewGroup, mStartView: View, mEndView: View) {
    TransitionManager.beginDelayedTransition(rootView, MaterialContainerTransform().apply {
        startView = mStartView
        endView = mEndView
        addTarget(mEndView)
        duration = 550L
        pathMotion = MaterialArcMotion()
        scrimColor = Color.TRANSPARENT
    })
}

// todo learn kotlin genericity
fun <T : ViewBinding> LifecycleOwner.inflateBinding(inflater: LayoutInflater): T {
    return (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        .filterIsInstance<Class<T>>()
        .first()
        .getDeclaredMethod("inflate", LayoutInflater::class.java)
        .invoke(null, inflater) as T
}