package com.zaiming.android.gallery.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.zaiming.android.gallery.BuildConfig
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.app.GlobalValue
import com.zaiming.android.gallery.utils.constantUtils.Constants
import com.zaiming.android.gallery.utils.sharedPreference.SpKeys
import com.zaiming.android.gallery.utils.windowInsets.applySystemBarImmersionMode
import com.zaiming.android.gallery.viewmodel.GalleryViewModel

/**
 * @author zaiming
 */
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private val galleryViewModel: GalleryViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<SwitchPreference>(Constants.showPicturesFiles)?.onPreferenceChangeListener = this
        findPreference<SwitchPreference>(Constants.showVideoFiles)?.onPreferenceChangeListener = this
        findPreference<Preference>(Constants.settingAppVersion)?.apply {
            summary = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        }
        findPreference<SwitchPreference>(Constants.sendErrorMessage)?.onPreferenceChangeListener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listView.apply {
            setDivider(null)
            applySystemBarImmersionMode()
        }
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        when (preference.key) {
            SpKeys.isCollectCrashInformation -> {
                GlobalValue.analyticsCollectInformationFromAppCenter.value = newValue as Boolean
                return true
            }
        }
        return false
    }
}
