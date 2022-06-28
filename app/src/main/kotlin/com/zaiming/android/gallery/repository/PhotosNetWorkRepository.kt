package com.zaiming.android.gallery.repository

import com.zaiming.android.gallery.galleryinterface.retrofit.GithubService
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Retrofit

/**
 * @author zaiming
 */

@Singleton
class PhotosNetWorkRepository @Inject constructor(private val retrofit: Retrofit) {

    private val githubService: GithubService by lazy {
        retrofit.create(GithubService::class.java)
    }

    suspend fun getGalleryAlbumRules() = githubService.getRules()
}