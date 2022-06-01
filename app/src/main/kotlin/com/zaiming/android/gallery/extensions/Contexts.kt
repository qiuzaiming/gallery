@file:kotlin.jvm.JvmName("ContextKts")

package com.zaiming.android.gallery.extensions

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.annotation.StyleRes

// function: add style to custom view constructor from drakeet
fun Context.toTheme(@StyleRes themeResId: Int): Context {
    return ContextThemeWrapper(this, themeResId)
}
