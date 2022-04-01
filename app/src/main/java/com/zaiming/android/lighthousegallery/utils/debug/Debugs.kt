package com.zaiming.android.lighthousegallery.utils.debug

import androidx.annotation.IntRange
import java.lang.StringBuilder

/**
 * @author zaiming
 *
 * get error stack traces
 */
object Debugs {

    fun stackTraces(self: Any? = null, @IntRange(from = 2) limit: Int = 3): String {
        val result = StringBuilder()
        var length = 0
        for ((i, it) in Thread.currentThread().stackTrace.withIndex()) {
            if (i < 4) continue
            if (length > 0) result.append(" <- ")
            if (self == null || self.javaClass.name != it.className) {
                result.append(it.className).append(".")
            }
            result.append("${it.methodName}(${it.lineNumber})")
            length++
            if (length >= limit) break
        }
        return result.toString()
    }
}
