package com.zaiming.android.lighthousegallery.extensions

import android.os.Build

fun requireApiQ(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
