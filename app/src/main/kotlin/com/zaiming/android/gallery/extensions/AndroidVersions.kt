@file:kotlin.jvm.JvmName("AndroidVersionKts")

package com.zaiming.android.gallery.extensions

import android.os.Build

fun requireApiQ(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
