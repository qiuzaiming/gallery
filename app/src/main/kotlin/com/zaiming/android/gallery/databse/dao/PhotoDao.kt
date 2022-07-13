package com.zaiming.android.gallery.databse.dao

import androidx.room.*
import com.zaiming.android.gallery.databse.entity.GalleryMetadata

@Dao
interface PhotoDao {

    @Query("SELECT * FROM gallery_metadata")
    suspend fun getAllMediaMetaData(): List<GalleryMetadata>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetMediaMetaData(data: List<GalleryMetadata>)

    @Delete
    suspend fun deleteMediaMetaData(data: List<GalleryMetadata>)

    @Query("SELECT * FROM gallery_metadata WHERE uri = (:uri)")
    suspend fun queryMediaMetaDataFromUri(uri: String): GalleryMetadata
}
