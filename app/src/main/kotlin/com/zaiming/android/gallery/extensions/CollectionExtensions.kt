package com.zaiming.android.gallery.extensions

import kotlin.math.ceil

const val SUB_LIST_LENGTH = 500

fun <T> List<T>.divideBy(maxSubListLength: Int = SUB_LIST_LENGTH): List<List<T>> {
    val count = ceil(size.toDouble() / maxSubListLength).toInt()
    val arr = arrayListOf<List<T>>()
    for (i in 0 until count) {
        arr.add(subList(maxSubListLength * i, (maxSubListLength * (i + 1)).coerceAtMost(size)))
    }
    return arr
}