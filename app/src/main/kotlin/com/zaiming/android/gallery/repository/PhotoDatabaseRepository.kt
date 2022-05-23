package com.zaiming.android.gallery.repository

import android.net.Uri
import com.zaiming.android.gallery.databse.dao.PhotoDao
import com.zaiming.android.gallery.databse.entity.MediaMetaData
import com.zaiming.android.gallery.extensions.divideBy
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoDatabaseRepository @Inject constructor(private val photoDao: PhotoDao) {


    suspend fun getAllMediaMetaData(): List<MediaMetaData> {
        return photoDao.getAllMediaMetaData()
    }

    suspend fun insertMediaMetaData(vararg item: MediaMetaData) {
        item.toList().divideBy().forEach {
            photoDao.insetMediaMetaData(it)
        }
    }

    suspend fun deleteMediaMetaData(vararg item: MediaMetaData) {
        item.toList().divideBy().forEach {
            photoDao.deleteMediaMetaData(it)
        }
    }

    suspend fun queryMediaMetaDataFromUri(uri: Uri): MediaMetaData {
        return photoDao.queryMediaMetaDataFromUri(uri.toString())
    }
}