package com.zaiming.android.gallery.ui

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.databinding.ActivityMainBinding
import com.zaiming.android.gallery.extensions.applyExitMaterialTransform
import com.zaiming.android.gallery.extensions.applyImmersionWithWindowInsets
import com.zaiming.android.gallery.extensions.imageContentUri
import com.zaiming.android.gallery.ui.fragment.AlbumsFragment
import com.zaiming.android.gallery.ui.fragment.PhotosFragment
import com.zaiming.android.gallery.ui.fragment.SelectedFragment
import com.zaiming.android.gallery.ui.fragment.SettingsFragment
import com.zaiming.android.gallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author zaiming
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val photosViewModel by lazy {
        ViewModelProvider(this)[GalleryViewModel::class.java]
    }
    private val needRequestMultiplePermission = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        if (it.values.any { value -> !value }) {
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

    private fun performClickNavigationItem(index: Int) {
        if (binding.viewpagerHostFragmentActivityMain.currentItem != index) {
            if (!binding.viewpagerHostFragmentActivityMain.isFakeDragging) {
                binding.viewpagerHostFragmentActivityMain.setCurrentItem(index, false)
            }
        } else {
            val isScrollAble = binding.viewpagerHostFragmentActivityMain.getTag(R.id.viewpager_click_event) as? Boolean ?: false
            if (!isScrollAble) {
                binding.viewpagerHostFragmentActivityMain.setTag(R.id.viewpager_click_event, true)
                lifecycleScope.launch {
                    delay(200)
                    binding.viewpagerHostFragmentActivityMain.setTag(R.id.viewpager_click_event, false)
                }
            } else {
                photosViewModel.controller?.scrollToTop()
            }
        }
    }

    companion object {
        private const val NAVIGATIONTABCOUNT = 4
    }
}
