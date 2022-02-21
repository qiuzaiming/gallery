package com.zaiming.android.lighthousegallery.utils.windowInsets

import android.app.Activity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.zaiming.android.lighthousegallery.R

class EdgeInsetDelegate(private val activity: Activity) {

    private fun isGestureNavigation(navigationBarsInsets: Insets): Boolean {
        val threshold = (20 * activity.resources.displayMetrics.density).toInt()
        // 44 is the fixed height of the IOS-like navigation bar(Gesture navigation), in pixels.
        return navigationBarsInsets.bottom <= threshold.coerceAtLeast(44)
    }

    /**
     * 0x21
     * light: 0x11
     */
    private fun isDarkModeActive(currentActivity: Activity): Boolean {
        return currentActivity.resources.configuration.uiMode == 0x21
    }

    fun start() {
        // Without this line of code, DecorView will consume the status and navigation bar
        // because in default, decorView can deal with status, navigation bar and ime -> set padding
        WindowCompat.setDecorFitsSystemWindows(activity.window, false)

        var eveGivenInsetsToDecorView = false
        //This prevents a translucent white bottom bar from appearing on the MIUI system
        activity.window.decorView.doOnApplyWindowInsets { windowInsets, _, _ ->
            val navigationBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())

            val isGestureNavigation = isGestureNavigation(navigationBarsInsets)

            if (!isGestureNavigation) {
                //Let decorView draw the translucent navigationBarColor
                ViewCompat.onApplyWindowInsets(activity.window.decorView, windowInsets)
                eveGivenInsetsToDecorView = true
            } else if (isGestureNavigation && eveGivenInsetsToDecorView) {
                //Let DecorView remove navigationBarBackground once it has been draw
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

        activity.window.navigationBarColor = if (isDarkModeActive(activity)) {
            activity.getColor(R.color.black_with_opacity_90)
        } else {
            activity.getColor(R.color.white_with_opacity_90)
        }
    }

}