package com.zaiming.android.gallery.utils

/**
 * @author zaiming
 */
class TimeRecorder {

    private var startTime: Long = 0L
    private var endTime: Long = 0L

    fun startTime() {
        startTime = System.currentTimeMillis()
    }

    fun endTime() {
        endTime = System.currentTimeMillis()
    }

    override fun toString(): String {
        return "time consuming ${(endTime - startTime)} ms"
    }
}