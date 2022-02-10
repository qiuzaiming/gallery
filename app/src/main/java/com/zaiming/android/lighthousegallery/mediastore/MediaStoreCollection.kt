package com.zaiming.android.lighthousegallery.mediastore

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.zaiming.android.lighthousegallery.bean.Asset
import com.zaiming.android.lighthousegallery.extensions.requireApiQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author zaiming
 */
class MediaStoreCollection {

    companion object {
        suspend inline fun fetchMediaContents(
            context: Context,
            columns: Array<String> = emptyArray(),
            contentUri: Uri,
            selection: String? = null,
            selectionArguments: Array<String>? = null,
            sortBy: String? = null,
            crossinline mapTo: (Asset, Cursor) -> Asset = { a, _ -> a }
        ): List<Asset> {
            val projection = arrayOf(
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.DATE_ADDED,
                MediaStore.MediaColumns.DATE_MODIFIED,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.SIZE,
                MediaStore.MediaColumns.MIME_TYPE,
                if (requireApiQ()) MediaStore.MediaColumns.RELATIVE_PATH else MediaStore.MediaColumns.DATA
            ) + columns

            try {
                return withContext(Dispatchers.IO) {
                    val assets = ArrayList<Asset>()
                    context.contentResolver.query(
                        contentUri,
                        projection,
                        selection,
                        selectionArguments,
                        sortBy
                    )?.use { c ->
                        val idColumn = c.getColumnIndexOrThrow(MediaStore.MediaColumns._ID)
                        val displayNameColumn =
                            c.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                        val sizeColumn = c.getColumnIndexOrThrow(MediaStore.MediaColumns.SIZE)
                        val dateAddedColumn =
                            c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_ADDED)
                        val dateModifiedColumn =
                            c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED)
                        val dataColumn = c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                        val mimeTypeColumn =
                            c.getColumnIndexOrThrow(MediaStore.MediaColumns.MIME_TYPE)
                        val relativePathColumn =
                            if (requireApiQ()) c.getColumnIndexOrThrow(MediaStore.MediaColumns.RELATIVE_PATH) else -1

                        while (c.moveToNext()) {
                            val id = c.getLong(idColumn)
                            val displayName = c.getString(displayNameColumn)
                            val size = c.getLong(sizeColumn)
                            val dateAdded = c.getLong(dateAddedColumn)
                            val dateModified = c.getLong(dateModifiedColumn)
                            val fullPath = c.getString(dataColumn)
                            val mimeType = c.getString(mimeTypeColumn)
                            val relativePath =
                                if (relativePathColumn >= 0) c.getString(relativePathColumn) else null

                            val asset = Asset(
                                id = id,
                                uri = ContentUris.withAppendedId(contentUri, id),
                                dateTimeAdded = dateAdded,
                                dateTimeModified = dateModified,
                                displayName = displayName,
                                size = size,
                                fullPath = fullPath,
                                mimeType = mimeType,
                                relativePath = relativePath
                            )
                            assets.add(mapTo(asset, c))
                        }
                    }
                    return@withContext assets
                }
            } catch (e: Exception) {
                e.printStackTrace()
                return emptyList()
            }
        }
    }
}