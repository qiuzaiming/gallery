package com.zaiming.android.lighthousegallery.viewmodel

import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaiming.android.lighthousegallery.adapter.SectionsAdapter
import com.zaiming.android.lighthousegallery.bean.Asset
import com.zaiming.android.lighthousegallery.bean.AssetLibrary
import com.zaiming.android.lighthousegallery.extensions.dateFormat
import com.zaiming.android.lighthousegallery.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

/**
 * @author zaiming
 */
@HiltViewModel
class PhotosViewModel @Inject constructor(private val photosRepository: PhotosRepository) : ViewModel() {

    init {
        listenerMediaStoreObserverInViewModel()
    }

    private var mediaStoreGroup = MutableStateFlow<MutableList<Asset>>(mutableListOf())

    fun asMediaStoreFlow() = mediaStoreGroup.map {
        it.sortedByDescending { createItem ->
            createItem.dateTimeModified
        }.groupBy { item ->
            item.dateTimeModified.dateFormat()
        }.map { processData ->
           SectionsAdapter.Section(processData.key, processData.value, Any())
        }
    }

     fun fetchMediaStoreInViewModel(
        columns: Array<String> = emptyArray(),
        contentUri: Uri,
        selection: String? = null,
        selectionArguments: Array<String>? = null,
        sortBy: String? = null,
        mapTo: (Asset, Cursor) -> Asset = { a, _ -> a }
    ) {
        viewModelScope.launch {
            mediaStoreGroup.value = photosRepository.fetchMediaStoreInRepository(columns, contentUri, selection, selectionArguments, sortBy, mapTo)
        }
     }

    private fun listenerMediaStoreObserverInViewModel() {
        viewModelScope.launch {
            photosRepository.observerMediaStoreObserverInRepository.collect { changeUri ->
                changeUri?.let {

                    //search mediaStore external.db to judge image/view exist?
                    val changeUriAsset = photosRepository.fetchMediaStoreInRepository(contentUri = it)

                    //todo has bug
                    mediaStoreGroup.value.add(changeUriAsset.first())
                }
            }
        }
    }

}

