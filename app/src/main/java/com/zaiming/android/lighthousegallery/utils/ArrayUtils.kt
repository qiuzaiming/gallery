package com.zaiming.android.lighthousegallery.utils

object ArrayUtils {

    fun <E> getItem(list: List<E>?, i: Int): E? {
        return if (list != null && i >= 0 && i < list.size) {
            list[i]
        } else null
    }


    fun size(collection: Collection<*>?): Int {
        return collection?.size ?: 0
    }


    fun size(map: Map<*, *>?): Int {
        return map?.size ?: 0
    }


    fun isEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    fun isSafeIndex(collection: Collection<*>?, index: Int): Boolean {
        return index >= 0 && index < size(collection)
    }
}