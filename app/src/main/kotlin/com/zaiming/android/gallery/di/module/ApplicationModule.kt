package com.zaiming.android.gallery.di.module

import android.app.Application
import com.zaiming.android.gallery.GalleryApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author zaiming
 */

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun providerGalleryApplication(@ApplicationContext application: Application): GalleryApplication {
        return application as GalleryApplication
    }
}
