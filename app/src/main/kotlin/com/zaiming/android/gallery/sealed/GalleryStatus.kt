package com.zaiming.android.gallery.sealed

import com.zaiming.android.gallery.databse.entity.GalleryMetadata

/**
 * @author zaiming
 */
sealed class GalleryStatus {
    data class AddMediaMetaData(val item: GalleryMetadata) : GalleryStatus()
    data class RemoveMediaMetaData(val item: GalleryMetadata) : GalleryStatus()
    data class UpdateGalleryProgress(val progress: Int) : GalleryStatus()
}
