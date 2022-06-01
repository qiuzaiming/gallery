@file:kotlin.jvm.JvmName("IntKts")

package com.zaiming.android.gallery.extensions

import android.content.Context
import android.util.TypedValue

context(Context)
val Int.dp: Float
    get() = this.toFloat().dp

context(Context)
val Int.sp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), resources.displayMetrics).toInt()