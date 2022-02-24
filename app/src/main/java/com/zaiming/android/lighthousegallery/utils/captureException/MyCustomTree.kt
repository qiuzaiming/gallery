package com.zaiming.android.lighthousegallery.utils.captureException

import timber.log.Timber

class MyCustomTree: Timber.Tree() {


    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {

    }

}