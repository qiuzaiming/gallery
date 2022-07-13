package com.zaiming.android.gallery.databse.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gallery_metadata")
data class GalleryMetadata(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,

    // uri
    @ColumnInfo(name = "uri") val uri: Uri,

    // size
    @ColumnInfo(name = "size") val size: Long,

    // mime_type
    @ColumnInfo(name = "mime_type") val mimeType: String,

    // relative_path
    @ColumnInfo(name = "relative_path") val relativePath: String,

    // data
    @ColumnInfo(name = "full_path") val fullPath: String
)
