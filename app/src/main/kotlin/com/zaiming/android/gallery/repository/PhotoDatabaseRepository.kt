package com.zaiming.android.gallery.repository

import com.zaiming.android.gallery.databse.dao.PhotoDao
import com.zaiming.android.gallery.databse.entity.MediaMetaData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoDatabaseRepository @Inject constructor(private val photoDao: PhotoDao) {


    suspend fun getAllMediaMetaData(): List<MediaMetaData> {
        return photoDao.getAllMediaMetaData()
    }

    suspend fun insertMediaMetaData(item: MediaMetaData) {
        photoDao.insetMediaMetaData(item)
    }
}