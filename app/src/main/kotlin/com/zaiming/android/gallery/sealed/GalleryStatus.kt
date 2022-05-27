package com.zaiming.android.gallery.sealed

import com.zaiming.android.gallery.databse.entity.MediaMetaData

/**
 * @author zaiming
 */
sealed class GalleryStatus {
    data class AddMediaMetaData(val item: MediaMetaData) : GalleryStatus()
    data class RemoveMediaMetaData(val item: MediaMetaData) : GalleryStatus()
    data class UpdateGalleryProgress(val progress: Int) : GalleryStatus()
}
