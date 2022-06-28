package com.zaiming.android.gallery.galleryinterface.retrofit

import com.zaiming.android.gallery.bean.retrofitBean.AlbumNameRules
import retrofit2.http.GET

/**
 * @author zaiming
 */
interface GithubService {

    @GET("qiuzaiming/rules/db")
    suspend fun getRules(): AlbumNameRules

}