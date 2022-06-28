package com.zaiming.android.gallery.viewmodel

import android.app.Application
import android.database.Cursor
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.zaiming.android.gallery.adapter.SectionsAdapter
import com.zaiming.android.gallery.bean.AlbumAsset
import com.zaiming.android.gallery.bean.Asset
import com.zaiming.android.gallery.bean.retrofitBean.App
import com.zaiming.android.gallery.databse.entity.GalleryMetadata
import com.zaiming.android.gallery.extensions.dateFormat
import com.zaiming.android.gallery.galleryinterface.IController
import com.zaiming.android.gallery.repository.PhotosNetWorkRepository
import com.zaiming.android.gallery.repository.PhotosRepository
import com.zaiming.android.gallery.sealed.GalleryStatus
import com.zaiming.android.gallery.utils.TimeRecorder
import com.zaiming.android.gallery.utils.constantUtils.Constants.SORT_BY_DATE_ADDED
import com.zaiming.android.gallery.utils.sharedPreference.SharedPreferenceUtils
import com.zaiming.android.gallery.utils.sharedPreference.SpKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author zaiming
 */
@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val photosRepository: PhotosRepository,
    application: Application,
    private val networkRepository: PhotosNetWorkRepository,
) : AndroidViewModel(application) {

    var controller: IController? = null

    private val _galleryStatus = MutableSharedFlow<GalleryStatus>()
    val galleryStatus = _galleryStatus.asSharedFlow()

    // album name rules from github api.
    private var galleryAlbumsNames = mutableListOf<App>()
    private var galleryAlbumNameDefer: Deferred<List<App>>? = null

    init {
        listenerMediaStoreObserverInViewModel()
        getAlbumRules()
    }

    private var mediaStoreGroup = MutableStateFlow<MutableList<Asset>>(mutableListOf())

    var sortCondition = MutableStateFlow(SharedPreferenceUtils.getValue(SpKeys.photosSortCondition, SORT_BY_DATE_ADDED))

    fun asMediaStoreFlow() = mediaStoreGroup.combine(sortCondition) { group, condition ->
        val destination = HashMap<String, ArrayList<Asset>>()
        group.sortedByDescending {
            if (condition == SORT_BY_DATE_ADDED) it.dateTimeAdded else it.dateTimeModified
        }.let {
            for (element in it) {
                val key = if (condition == SORT_BY_DATE_ADDED) element.dateTimeAdded.dateFormat() else element.dateTimeModified.dateFormat()
                val list = destination.getOrPut(key) { ArrayList() }
                list.add(element)
            }
        }
        destination.map { processData ->
            SectionsAdapter.Section(processData.key, processData.value, Any())
        }
    }

    suspend fun asAlbumMediaStoreFlow() = mediaStoreGroup.map {
        it.sortedByDescending { createItem ->
            createItem.dateTimeModified
        }.groupBy { item ->
            getActualAlbumName(item.relativePath)
        }.map { processData ->
            AlbumAsset(processData.key, processData.value.size, processData.value.firstOrNull()?.uri?.toString().orEmpty())
        }
    }

    private suspend fun getActualAlbumName(relativePath: String?): String {

        if (galleryAlbumNameDefer != null) {
            galleryAlbumNameDefer?.await()
            galleryAlbumNameDefer = null
        }

       return galleryAlbumsNames.firstOrNull {
               it.path == relativePath
           }?.locales?.zh ?: ""
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
            val timeRecorder = TimeRecorder()
            timeRecorder.startTime()
            mediaStoreGroup.value = photosRepository.fetchMediaStoreInRepository(columns, contentUri, selection, selectionArguments, sortBy, mapTo)
            timeRecorder.endTime()
            Timber.i("fetchMediaStoreInViewModel: $timeRecorder")
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

    fun updateGalleryProgress(progress: Int) {
        setGalleryStatus {
            GalleryStatus.UpdateGalleryProgress(progress)
        }
    }

    fun addMediaMetaData(addItem: GalleryMetadata) {
        setGalleryStatus {
            GalleryStatus.AddMediaMetaData(addItem)
        }
    }

    fun removeMediaMetaData(removeItem: GalleryMetadata) {
        setGalleryStatus {
            GalleryStatus.RemoveMediaMetaData(removeItem)
        }
    }

    private fun getAlbumRules() {
        galleryAlbumNameDefer = viewModelScope.async(Dispatchers.IO) {
            galleryAlbumsNames = networkRepository.getGalleryAlbumRules().apps.toMutableList()
            galleryAlbumsNames
       }
    }

    private fun setGalleryStatus(build: () -> GalleryStatus) {
        viewModelScope.launch {
            _galleryStatus.emit(build())
        }
    }
}
