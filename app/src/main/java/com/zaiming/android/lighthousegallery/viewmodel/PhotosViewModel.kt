package com.zaiming.android.lighthousegallery.viewmodel

import androidx.lifecycle.ViewModel
import com.zaiming.android.lighthousegallery.bean.Asset
import com.zaiming.android.lighthousegallery.bean.AssetLibrary
import com.zaiming.android.lighthousegallery.extensions.dateFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author zaiming
 */
class PhotosViewModel : ViewModel() {

    private val mediaStoreGroup = MutableStateFlow<List<Asset>>(emptyList())

    fun emitMediaStoreGroup(mediaStoreData: List<Asset>) {
        mediaStoreGroup.value = mediaStoreData
    }

    fun asMediaStoreFlow() = mediaStoreGroup.map {
        it.sortedByDescending { createItem ->
            createItem.dateTimeModified
        }.groupBy { item ->
            item.dateTimeModified.dateFormat()
        }.map { processData ->
            AssetLibrary(processData.key, processData.value)
        }
    }

}

