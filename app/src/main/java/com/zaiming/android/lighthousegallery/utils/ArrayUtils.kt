package com.zaiming.android.lighthousegallery.utils

import com.zaiming.android.lighthousegallery.utils.debug.Debugs
import timber.log.Timber

object ArrayUtils {

    fun <E> getItem(list: List<E>?, i: Int): E? {
        return if (list != null && i >= 0 && i < list.size) {
            list[i]
        } else {
            printCollectionErrorLog()
            null
        }
    }

    fun size(collection: Collection<*>?): Int {
        if (collection == null) {
            printCollectionErrorLog()
        }
        return collection?.size ?: 0
    }

    fun size(map: Map<*, *>?): Int {
        if (map == null) {
            printCollectionErrorLog()
        }
        return map?.size ?: 0
    }

    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun isSafeIndex(collection: Collection<*>?, index: Int): Boolean {
        return index >= 0 && index < size(collection)
    }

    private fun printCollectionErrorLog() {
        Timber.e(Debugs.stackTraces())
    }
}
