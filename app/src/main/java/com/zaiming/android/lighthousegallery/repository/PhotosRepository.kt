package com.zaiming.android.lighthousegallery.repository

import android.database.Cursor
import android.net.Uri
import com.zaiming.android.lighthousegallery.bean.Asset
import com.zaiming.android.lighthousegallery.mediastore.MediaStoreCollection
import javax.inject.Inject

class PhotosRepository @Inject constructor(private val mediaStoreCollection: MediaStoreCollection) {

    suspend fun fetchMediaStoreInRepository(
        columns: Array<String> = emptyArray(),
        contentUri: Uri,
        selection: String? = null,
        selectionArguments: Array<String>? = null,
        sortBy: String? = null,
        mapTo: (Asset, Cursor) -> Asset = { a, _ -> a }
    ): List<Asset> {
        return mediaStoreCollection.fetchMediaContents(columns, contentUri, selection, selectionArguments, sortBy, mapTo)
    }

}