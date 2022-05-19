package com.zaiming.android.gallery.databse.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zaiming.android.gallery.databse.entity.MediaMetaData

@Dao
interface PhotoDao {

    @Query("SELECT * FROM media_metadata")
    suspend fun getAllMediaMetaData(): List<MediaMetaData>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetMediaMetaData(data: MediaMetaData)

}