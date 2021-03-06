package com.zaiming.android.gallery.utils.windowInsets

import android.app.Activity
import androidx.core.content.ContextCompat
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.zaiming.android.gallery.R

class EdgeInsetDelegate(private val activity: Activity) {

    private fun isGestureNavigation(navigationBarsInsets: Insets): Boolean {
        val threshold = (20 * activity.resources.displayMetrics.density).toInt()
        // 44 is the fixed height of the IOS-like navigation bar(Gesture navigation), in pixels.
        return navigationBarsInsets.bottom <= threshold.coerceAtLeast(44)
    }

    /**
     * dark: 0x21
     * light: 0x11
     */
    private fun isDarkModeActive(currentActivity: Activity): Boolean {
        return currentActivity.resources.configuration.uiMode == 0x21
    }

    fun start() {
        // Without this line of code, DecorView will consume the status and navigation bar
        // because in default, decorView can deal with status, navigation bar and ime -> set padding
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)

        activity.window.navigationBarColor = if (isDarkModeActive(activity)) {
            ContextCompat.getColor(activity, R.color.black_with_opacity_90)
        } else {
            ContextCompat.getColor(activity, R.color.white_with_opacity_90)
        }

        var eveGivenInsetsToDecorView = false
        // This prevents a translucent white bottom bar from appearing on the MIUI system
        activity.window.decorView.doOnApplyWindowInsets { _, windowInsets, _, _ ->
            val navigationBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())

            val isGestureNavigation = isGestureNavigation(navigationBarsInsets)

            if (!isGestureNavigation) {
                // Let decorView draw the translucent navigationBarColor
                // setting ViewCompat.onApplyWindowInsets(activity.window.decorView, windowInsets) means that windowInsets give decorView to handle
                // so decorView decors statusBar and navigationBar colors -> you set statusBar color or navigationBar color take effect
                // by the way, you do not set  ViewCompat.onApplyWindowInsets(activity.window.decorView, windowInsets) and setting navigationBar or statusBar -> not effect

                // Be consistent with whether isGestureNavigation
                // ViewCompat.onApplyWindowInsets(activity.window.decorView, windowInsets)
                eveGivenInsetsToDecorView = true
            } else if (isGestureNavigation && eveGivenInsetsToDecorView) {
                // Let DecorView remove navigationBarBackground once it has been draw
                ViewCompat.onApplyWindowInsets(
                    activity.window.decorView,
                    WindowInsetsCompat.Builder()
                        .setInsets(
                            WindowInsetsCompat.Type.navigationBars(),
                            Insets.of(navigationBarsInsets.left, navigationBarsInsets.top, navigationBarsInsets.right, 0)
                        ).build()
                )
            } else {
                // else: Because we intercepted the onApplyWindowInsets of decorView,
                // navigationBarColor will not be used, which means that the navigation bar
                // will be completely transparent
            }
        }
    }
}
