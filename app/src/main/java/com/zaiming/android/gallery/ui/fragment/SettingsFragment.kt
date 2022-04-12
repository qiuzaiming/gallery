package com.zaiming.android.gallery.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.utils.Constants
import com.zaiming.android.gallery.utils.windowInsets.applySystemBarImmersionMode
import com.zaiming.android.gallery.viewmodel.GalleryViewModel

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
        listView.applySystemBarImmersionMode()
    }

    override fun onPreferenceChange(preference: Preference, newValue: Any?): Boolean {
        return false
    }
}
