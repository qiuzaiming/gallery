package com.zaiming.android.gallery.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.behavior.HideBottomViewOnScrollBehavior
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView.LABEL_VISIBILITY_LABELED
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.analytics.EventProperties
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.base.BaseActivity
import com.zaiming.android.gallery.databinding.ActivityMainBinding
import com.zaiming.android.gallery.extensions.applyExitMaterialTransform
import com.zaiming.android.gallery.extensions.applyImmersionWithWindowInsets
import com.zaiming.android.gallery.extensions.imageContentUri
import com.zaiming.android.gallery.galleryinterface.INavController
import com.zaiming.android.gallery.ui.fragment.AlbumsFragment
import com.zaiming.android.gallery.ui.fragment.PhotosFragment
import com.zaiming.android.gallery.ui.fragment.SelectedFragment
import com.zaiming.android.gallery.ui.fragment.SettingsFragment
import com.zaiming.android.gallery.utils.constantUtils.AppCenterConstants
import com.zaiming.android.gallery.utils.constantUtils.ShortCutConstants
import com.zaiming.android.gallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author zaiming
 */
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(), INavController {

    private val photosViewModel by viewModels<GalleryViewModel>()
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
    private val hideBottomViewOnScrollBehavior by lazy {
        HideBottomViewOnScrollBehavior<BottomNavigationView>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applyExitMaterialTransform()
        super.onCreate(savedInstanceState)

        applyImmersionWithWindowInsets()
        initView()
        requestPermissionLauncher.launch(needRequestMultiplePermission)
        startActionFromShortCutAction(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        startActionFromShortCutAction(intent)
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

                (layoutParams as CoordinatorLayout.LayoutParams).behavior = hideBottomViewOnScrollBehavior

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
            } else if (photosViewModel.controller?.isAllowScrollToTop() == true) {
                photosViewModel.controller?.scrollToTop()
            }
        }
    }

    private fun startActionFromShortCutAction(intent: Intent) {
        when(intent.action) {
            ShortCutConstants.ACTION_START_PHOTOS -> binding.viewpagerHostFragmentActivityMain.setCurrentItem(0, false)
            ShortCutConstants.ACTION_START_ALBUM ->  binding.viewpagerHostFragmentActivityMain.setCurrentItem(1, false)
        }
        Analytics.trackEvent(AppCenterConstants.ACTION_LAUNCH, EventProperties().set(AppCenterConstants.ACTION_LAUNCH, intent.action))
    }

    companion object {
        private const val NAVIGATIONTABCOUNT = 4
    }

    override fun slideUp() {
        hideBottomViewOnScrollBehavior.slideUp(binding.navView)
    }

    override fun slideDown() {
        hideBottomViewOnScrollBehavior.slideDown(binding.navView)
    }
}
