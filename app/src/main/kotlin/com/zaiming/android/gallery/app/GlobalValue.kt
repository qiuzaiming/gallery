package com.zaiming.android.gallery.app

import androidx.lifecycle.MutableLiveData
import com.zaiming.android.gallery.utils.sharedPreference.SharedPreferenceUtils
import com.zaiming.android.gallery.utils.sharedPreference.SpKeys.isCollectCrashInformation

/**
 * @author zaiming
 */
object GlobalValue {

    var analyticsCollectInformationFromAppCenter = MutableLiveData(SharedPreferenceUtils.getValue(isCollectCrashInformation, true))
}
