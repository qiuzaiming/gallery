package com.zaiming.android.gallery.bean.retrofitBean

/**
 * Get https://my-json-server.typicode.com/qiuzaiming/rules/db
 */
data class SimluateBean(
    val posts: List<Post>,
    val profile: Profile
)

data class Profile(
    val name: String
)

data class Post(
    val id: Int,
    val title: String
)