package com.zaiming.android.lighthousegallery.mediastore

import android.content.Context
import android.database.ContentObserver
import android.net.Uri
import android.os.Handler
import android.os.HandlerThread
import com.zaiming.android.lighthousegallery.extensions.imageContentUri
import com.zaiming.android.lighthousegallery.extensions.videoContentUri
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author zaiming
 */
@Singleton
class MediaStoreObserver @Inject constructor(@ApplicationContext private val context: Context) {

    private lateinit var innerObserver: InnerObserver

    fun listenerMediaStoreChange(): MutableStateFlow<Uri?> {
        val handlerThread = HandlerThread(mediaStoreHandlerThreadName)
        handlerThread.start()
        innerObserver = InnerObserver(Handler(handlerThread.looper))
        context.contentResolver.apply {
            registerContentObserver(imageContentUri(), true, innerObserver)
            registerContentObserver(videoContentUri(), true, innerObserver)
        }
        return innerObserver.mediaStoreOnChangeUri
    }


    companion object {
        private const val mediaStoreHandlerThreadName = "GalleryMediaStoreObserver"
    }

}

private class InnerObserver(handler: Handler): ContentObserver(handler) {

    private var innerMediaStoreOnChangeUri = MutableStateFlow<Uri?>(null)
    val mediaStoreOnChangeUri
        get() = innerMediaStoreOnChangeUri

    override fun onChange(selfChange: Boolean, uri: Uri?) {
        super.onChange(selfChange, uri)
        innerMediaStoreOnChangeUri.value = uri
    }

}