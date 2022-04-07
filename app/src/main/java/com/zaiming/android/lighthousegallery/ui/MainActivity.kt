package com.zaiming.android.lighthousegallery.ui

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED
import com.zaiming.android.lighthousegallery.R
import com.zaiming.android.lighthousegallery.databinding.ActivityMainBinding
import com.zaiming.android.lighthousegallery.extensions.applyExitMaterialTransform
import com.zaiming.android.lighthousegallery.extensions.applyImmersionWithWindowInsets
import com.zaiming.android.lighthousegallery.extensions.imageContentUri
import com.zaiming.android.lighthousegallery.ui.fragment.AlbumsFragment
import com.zaiming.android.lighthousegallery.ui.fragment.PhotosFragment
import com.zaiming.android.lighthousegallery.ui.fragment.SelectedFragment
import com.zaiming.android.lighthousegallery.ui.fragment.SettingsFragment
import com.zaiming.android.lighthousegallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author zaiming
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val photosViewModel by lazy {
        ViewModelProvider(this)[GalleryViewModel::class.java]
    }
    private val needRequestMultiplePermission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.values.any { value -> value == false }) {
            finish()
            return@registerForActivityResult
        } else {
            // agree all permission
            photosViewModel.fetchMediaStoreInViewModel(contentUri = imageContentUri())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applyExitMaterialTransform()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyImmersionWithWindowInsets()
        initView()
        requestPermissionLauncher.launch(needRequestMultiplePermission)
    }

    private fun initView() {
        binding.apply {
            viewpagerHostFragmentActivityMain.apply {
                adapter = object : FragmentStateAdapter(this@MainActivity) {
                    override fun getItemCount(): Int {
                        return NAVIGATIONTABCOUNT
                    }

                    override fun createFragment(position: Int): Fragment {
                        return when (position) {
                            0 -> PhotosFragment()
                            1 -> AlbumsFragment()
                            2 -> SelectedFragment()
                            else -> SettingsFragment()
                        }
                    }
                }

                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        binding.navView.menu.getItem(position).isChecked = true
                    }
                })

                // forbid left and right scroll
                isUserInputEnabled = false
                offscreenPageLimit = 2
            }

            navView.apply {

                // display full BottomNavigationView
                labelVisibilityMode = LABEL_VISIBILITY_LABELED

                setOnItemSelectedListener {

                    fun performClickNavigationItem(index: Int) {
                        if (binding.viewpagerHostFragmentActivityMain.currentItem != index) {
                            if (!binding.viewpagerHostFragmentActivityMain.isFakeDragging) {
                                binding.viewpagerHostFragmentActivityMain.setCurrentItem(index, true)
                            }
                        }
                    }

                    when (it.itemId) {
                        R.id.navigation_photos -> performClickNavigationItem(0)
                        R.id.navigation_albums -> performClickNavigationItem(1)
                        R.id.navigation_selected -> performClickNavigationItem(2)
                        R.id.navigation_settings -> performClickNavigationItem(3)
                    }
                    true
                }
            }
        }
    }

    companion object {
        private const val NAVIGATIONTABCOUNT = 4
    }
}
