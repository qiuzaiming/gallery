package com.zaiming.android.lighthousegallery.extensions

import android.content.Context
import android.util.TypedValue

context(Context)
val Int.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), resources.displayMetrics).toInt()

context(Context)
val Int.px: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, this.toFloat(), resources.displayMetrics).toInt()