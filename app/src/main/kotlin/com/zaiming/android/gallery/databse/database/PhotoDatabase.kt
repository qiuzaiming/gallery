package com.zaiming.android.gallery.databse.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.zaiming.android.gallery.databse.converters.Converters
import com.zaiming.android.gallery.databse.dao.PhotoDao
import com.zaiming.android.gallery.databse.entity.MediaMetaData

/**
 * @author zaiming
 */
@Database(
    entities = [MediaMetaData::class], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {

        @Volatile
        private var INSTANCE: PhotoDatabase? = null

        fun getDatabase(context: Context): PhotoDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                    PhotoDatabase::class.java,
                    "photo_database")
                    .build().also { INSTANCE = it }
            }
        }
    }

}