package com.zaiming.android.gallery.databse.converters

import android.net.Uri
import androidx.room.TypeConverter

/**
 * @author zaiming
 */
class Converters {

    @TypeConverter
    fun stringToUri(value: String?): Uri? = value?.let { Uri.parse(it) }

    @TypeConverter
    fun uriToString(uri: Uri?) = uri?.toString()
}