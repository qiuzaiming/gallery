package com.zaiming.android.lighthousegallery.utils.windowInsets

import android.view.View
import android.view.ViewGroup
import androidx.core.view.*

class InitialPadding(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

class InitialMargin(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

fun recordInitialPaddingForView(view: View) = InitialPadding(
    view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom
)

fun recordInitialMarginForView(view: View) = InitialMargin(
    view.marginLeft, view.marginTop, view.marginRight, view.marginBottom
)

/**
 * It is best to always call this method when setting OnApplyWindowInsetsListener,
 * otherwise OnApplyWindowInsetsListener will not be called sometimes, such as when
 * we set OnApplyWindowInsetsListener in the constructor of a view and this view will
 * be added to the ViewGroup after a delay.
 */
fun View.requestApplyInsetsWhenAttached() {
    if (isAttachedToWindow) {
        requestApplyInsets()
    } else {
        addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(v: View) {
                v.removeOnAttachStateChangeListener(this)
                v.requestApplyInsets()
            }

            override fun onViewDetachedFromWindow(v: View) = Unit

        })
    }
}

fun View.doOnApplyWindowInsets(block: (WindowInsetsCompat, InitialPadding, InitialMargin) -> Unit) {
    val initialPadding = recordInitialPaddingForView(this)
    val initialMargin = recordInitialMarginForView(this)
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        block(insets, initialPadding, initialMargin)
        insets
    }
    requestApplyInsetsWhenAttached()
}

fun View.applyBottomWindowInsetsForScrollingView(scrollingView: ViewGroup) {
    scrollingView.clipToPadding = false
    val scrollingViewInitialPadding = recordInitialPaddingForView(scrollingView)
    this.doOnApplyWindowInsets { insets, _, _ ->
        scrollingView.updatePadding(bottom = scrollingViewInitialPadding.bottom + insets.systemWindowInsetBottom)
    }
}

fun View.applyTopWindowInsetsForScrollingView(scrollingView: ViewGroup) {
    scrollingView.clipToPadding = false
    val scrollingViewInitialPadding = recordInitialPaddingForView(scrollingView)
    this.doOnApplyWindowInsets { insets, _, _ ->
        scrollingView.updatePadding(top = scrollingViewInitialPadding.top + insets.systemWindowInsetTop)
    }
}