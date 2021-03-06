package com.zaiming.android.gallery.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.zaiming.android.gallery.BuildConfig
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.app.GlobalValue
import com.zaiming.android.gallery.ui.activity.AboutActivity
import com.zaiming.android.gallery.utils.constantUtils.SettingConstants
import com.zaiming.android.gallery.utils.sharedPreference.SpKeys
import com.zaiming.android.gallery.utils.windowInsets.applySystemBarImmersionMode
import com.zaiming.android.gallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author zaiming
 */
@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private val galleryViewModel: GalleryViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<SwitchPreference>(SettingConstants.showPicturesFiles)?.onPreferenceChangeListener = this
        findPreference<SwitchPreference>(SettingConstants.showVideoFiles)?.onPreferenceChangeListener = this
        findPreference<Preference>(SettingConstants.settingAppVersion)?.apply {
            summary = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
            onPreferenceClickListener = this@SettingsFragment
        }
        findPreference<SwitchPreference>(SettingConstants.sendErrorMessage)?.onPreferenceChangeListener = this
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

    override fun onPreferenceClick(preference: Preference): Boolean {
        when(preference.key) {
            SettingConstants.settingAppVersion -> startActivity(Intent(requireContext(), AboutActivity::class.java))
        }
        return true
    }
}
