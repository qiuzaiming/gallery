package com.zaiming.android.gallery.extensions

import android.content.Context
import android.util.TypedValue


context(Context)
val Float.dp: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics)