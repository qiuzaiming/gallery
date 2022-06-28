package com.zaiming.android.gallery.bean.retrofitBean

/**
 * GET https://my-json-server.typicode.com/qiuzaiming/rules/db
 */
data class AlbumNameRules(
    val apps: List<App>,
    val version: Version
)

data class App(
    val locales: Locales,
    val name: String,
    val path: String
)

data class Locales(
    val zh: String
)

data class Version(
    val name: Int
)