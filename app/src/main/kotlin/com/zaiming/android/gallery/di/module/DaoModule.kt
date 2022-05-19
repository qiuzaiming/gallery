package com.zaiming.android.gallery.di.module

import android.app.Application
import com.zaiming.android.gallery.databse.dao.PhotoDao
import com.zaiming.android.gallery.databse.database.PhotoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author zaiming
 */

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {

    @Singleton
    @Provides
    fun providerPhotoDao(application: Application): PhotoDao {
        return PhotoDatabase.getDatabase(application).photoDao()
    }
}