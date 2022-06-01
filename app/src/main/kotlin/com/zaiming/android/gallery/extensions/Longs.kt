@file:kotlin.jvm.JvmName("LongKts")

package com.zaiming.android.gallery.extensions

import android.text.format.Formatter
import com.zaiming.android.gallery.GalleryApplication
import com.zaiming.android.gallery.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * time change to standard form
 * because mediaStore date time / 1000 save in external {@link Pho.java}
 * so recover the time need * 1000
 */
fun Long.dateFormat(
    pattern: String = GalleryApplication.instance.resources.getString(R.string.date_format_by_day),
    locale: Locale = Locale.getDefault(),
): String {
    val simpleDateFormat = SimpleDateFormat(pattern, locale)
    return simpleDateFormat.format(Date(this * 1000))
}

fun Long.formatFileSize(): String = Formatter.formatFileSize(GalleryApplication.instance, this)
