package com.zaiming.android.lighthousegallery.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.zaiming.android.lighthousegallery.R
import com.zaiming.android.lighthousegallery.utils.Constants
import com.zaiming.android.lighthousegallery.utils.windowInsets.doOnApplyWindowInsets
import com.zaiming.android.lighthousegallery.viewmodel.GalleryViewModel

/**
 * @author zaiming
 */
class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    private val galleryViewModel: GalleryViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference<SwitchPreferenceCompat>(Constants.showPicturesFiles)?.onPreferenceChangeListener = this
        findPreference<SwitchPreferenceCompat>(Constants.showVideoFiles)?.onPreferenceChangeListener = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingWindowInsetsParamsOnRecyclerView()
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        return false
    }

    private fun settingWindowInsetsParamsOnRecyclerView() {
        listView.doOnApplyWindowInsets { view, windowInsetsCompat, _, _ ->
            with(view) {
                setPaddingRelative(
                    paddingStart,
                    paddingTop + windowInsetsCompat.systemWindowInsetTop,
                    paddingEnd + windowInsetsCompat.systemWindowInsetRight,
                    paddingBottom + windowInsetsCompat.systemWindowInsetBottom
                )
            }
        }
    }
}
