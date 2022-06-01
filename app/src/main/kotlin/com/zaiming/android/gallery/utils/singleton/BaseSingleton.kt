package com.zaiming.android.gallery.utils.singleton

/**
 * @author zaiming
 */
abstract class BaseSingleton<in P, out T> {

    @Volatile private var INSTANCE: T? = null

    protected abstract fun createSingleton(param: P): T

    fun getInstance(param: P): T {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: createSingleton(param).also { INSTANCE = it }
        }
    }
}