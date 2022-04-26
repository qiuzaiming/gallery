package com.zaiming.android.gallery.viewmodel

import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaiming.android.gallery.adapter.SectionsAdapter
import com.zaiming.android.gallery.bean.AlbumAsset
import com.zaiming.android.gallery.bean.Asset
import com.zaiming.android.gallery.extensions.dateFormat
import com.zaiming.android.gallery.galleryinterface.IController
import com.zaiming.android.gallery.repository.PhotosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author zaiming
 */
@HiltViewModel
class GalleryViewModel @Inject constructor(private val photosRepository: PhotosRepository) : ViewModel() {

    var controller: IController? = null

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

    fun asAlbumMediaStoreFlow() = mediaStoreGroup.map {
        it.sortedByDescending { createItem ->
            createItem.dateTimeModified
        }.groupBy { item ->
            item.relativePath ?: ""
        }.map { processData ->
            AlbumAsset(processData.key, processData.value.size, processData.value.firstOrNull()?.uri?.toString() ?: "")
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

                    // search mediaStore external.db to judge image/view exist?
                    val changeUriAsset = photosRepository.fetchMediaStoreInRepository(contentUri = it)

                    if (changeUriAsset.isEmpty()) {
                        // delete action
                        mediaStoreGroup.value.find { changeAsset ->
                            changeAsset.uri == it
                        }?.let { deleteAssetItem ->
                            mediaStoreGroup.value = (mediaStoreGroup.value - deleteAssetItem).toMutableList()
                        }
                    }

                    // add action
                    changeUriAsset.firstNotNullOf { newAsset ->
                        if (!mediaStoreGroup.value.contains(newAsset)) {
                            mediaStoreGroup.value = (mediaStoreGroup.value + newAsset).toMutableList()
                        }
                    }
                }
            }
        }
    }
}
