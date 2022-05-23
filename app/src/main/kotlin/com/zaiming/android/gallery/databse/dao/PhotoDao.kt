package com.zaiming.android.gallery.databse.dao

import androidx.room.*
import com.zaiming.android.gallery.databse.entity.MediaMetaData

@Dao
interface PhotoDao {

    @Query("SELECT * FROM media_metadata")
    suspend fun getAllMediaMetaData(): List<MediaMetaData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetMediaMetaData(data: List<MediaMetaData>)

    @Delete
    suspend fun deleteMediaMetaData(data: List<MediaMetaData>)

    @Query("SELECT * FROM media_metadata WHERE uri = (:uri)")
    suspend fun queryMediaMetaDataFromUri(uri: String): MediaMetaData

}