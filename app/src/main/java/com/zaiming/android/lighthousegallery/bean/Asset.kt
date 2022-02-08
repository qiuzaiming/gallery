package com.zaiming.android.lighthousegallery.bean

import android.net.Uri

/**
 * @author zaiming
 */
data class Asset(
    val id: Long,
    var uri: Uri,
    var dateTimeAdded: Long = 0L,
    var dateTimeModified: Long = 0L,
    val displayName: String,
    var size: Long = 0L,
    val fullPath: String,
    val mimeType: String,
    val relativePath: String?,
    var duration: Long = 0L
)
