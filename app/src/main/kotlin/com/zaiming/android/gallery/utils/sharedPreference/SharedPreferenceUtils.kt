package com.zaiming.android.gallery.utils.sharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.zaiming.android.gallery.BuildConfig
import com.zaiming.android.gallery.GalleryApplication

private const val SP_NAME = "${BuildConfig.APPLICATION_ID}_shared_preference"

object SharedPreferenceUtils {

    private val sp: SharedPreferences by lazy {
        GalleryApplication.instance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    fun <T> getValue(key: String, defaultValue: T): T = with(sp){
        val value = when (defaultValue) {
            is Boolean -> getBoolean(key, defaultValue)
            is Int -> getInt(key, defaultValue)
            is Long -> getLong(key, defaultValue)
            is Float -> getFloat(key, defaultValue)
            is String -> getString(key, defaultValue)
            else -> throw IllegalArgumentException("Unsupported type.")
        }
        value as T
    }

    fun <T> putValue(key: String, value: T) = with(sp.edit()) {
        when(value) {
            is Boolean -> putBoolean(key, value)
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is String -> putString(key, value)
            else -> throw IllegalArgumentException("Unsupported type.")
        }.apply()
    }

}