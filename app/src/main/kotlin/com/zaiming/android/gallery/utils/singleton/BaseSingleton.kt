package com.zaiming.android.gallery.utils.singleton

/**
 * @author zaiming
 */
abstract class BaseSingleton<in P, out T> {

    @Volatile private var INSTANCE: T? = null

    protected abstract val creator: (P) -> T

    fun getInstance(param: P): T {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: creator(param).also { INSTANCE = it }
        }
    }
}