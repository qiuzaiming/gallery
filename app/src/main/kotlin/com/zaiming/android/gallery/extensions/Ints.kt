@file:kotlin.jvm.JvmName("IntKts")

package com.zaiming.android.gallery.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

context(Context)
val Int.dp: Float
    get() = this.toFloat().dp

context(Context)
val Int.sp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), resources.displayMetrics).toInt()

fun @receiver:DrawableRes Int.getDrawable(context: Context): Drawable? = ContextCompat.getDrawable(context, this)

fun @receiver:ColorRes Int.getColor(context: Context): Int = ContextCompat.getColor(context, this)

fun @receiver:StringRes Int.getString(context: Context): String = context.getString(this)