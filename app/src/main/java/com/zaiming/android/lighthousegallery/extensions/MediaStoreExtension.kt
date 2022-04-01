package com.zaiming.android.lighthousegallery.extensions

import android.net.Uri
import android.provider.MediaStore

fun imageContentUri(): Uri {
    return MediaStore.Images.Media.EXTERNAL_CONTENT_URI
}

fun videoContentUri(): Uri {
    return MediaStore.Video.Media.EXTERNAL_CONTENT_URI
}
