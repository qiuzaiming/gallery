package com.zaiming.android.gallery.utils.sharedPreference

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author zaiming
 */
class SPDelegate<T>(private val key: String, private val value: T) : ReadWriteProperty<Any?, T> {

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return SharedPreferenceUtils.getValue(key, value)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        SharedPreferenceUtils.putValue(key, value)
    }

}