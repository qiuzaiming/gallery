package com.zaiming.android.gallery.extensions

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.selects.select

suspend fun <T : Any> fastDeferred(vararg deferreds: Deferred<T>): T = select {
    fun cancelAllDeferReds() = deferreds.forEach { it.cancel() }

    deferreds.forEach {
        it.onAwait { value ->
            cancelAllDeferReds()
            value
        }
    }
}
