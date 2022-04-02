package com.zaiming.android.lighthousegallery.extensions

import android.content.Context
import android.view.ContextThemeWrapper

// function: add style to custom view constructor from drakeet
fun Context.toTheme(themeResId: Int): Context {
    return ContextThemeWrapper(this, themeResId)
}
