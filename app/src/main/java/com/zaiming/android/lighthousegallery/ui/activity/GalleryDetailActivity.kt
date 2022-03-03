package com.zaiming.android.lighthousegallery.ui.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import com.bumptech.glide.Glide
import com.zaiming.android.lighthousegallery.R
import com.zaiming.android.lighthousegallery.databinding.ActivityGalleryDetailBinding
import com.zaiming.android.lighthousegallery.extensions.applyImmersionWithWindowInsets
import com.zaiming.android.lighthousegallery.extensions.applyMaterialTransform
import com.zaiming.android.lighthousegallery.utils.windowInsets.EdgeInsetDelegate

/**
 * @author zaiming
 */
class GalleryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGalleryDetailBinding

    private val galleryDetailTransientName by lazy {
        intent.getStringExtra(EXTRA_TRANSITION_NAME)
    }

    private val galleryDetailUri by lazy {
        intent.getStringExtra(EXTRA_URL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        postponeEnterTransition()
        applyMaterialTransform(galleryDetailTransientName)
        super.onCreate(savedInstanceState)
        binding = ActivityGalleryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyImmersionWithWindowInsets()

        binding.ivDetail.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    override fun onStart() {
        super.onStart()

        Glide.with(this)
            .load(galleryDetailUri)
            .into(binding.ivDetail)
    }

    private fun settingWindowInset() {
        window.statusBarColor = Color.TRANSPARENT
        ViewCompat.getWindowInsetsController(binding.root)?.apply {
            // change statusBar text to black color.
            isAppearanceLightStatusBars = true
        }
        EdgeInsetDelegate(this).start()
    }

    companion object {
        private const val EXTRA_URL = "EXTRA_URL"
        private const val EXTRA_TRANSITION_NAME = "EXTRA_TRANSITION_NAME"

        fun startActivity(activity: Activity, startView: View, transitionName: String, currentUriPath: String) {
            val options = ActivityOptions.makeSceneTransitionAnimation(activity, startView, transitionName)
            val intent = Intent(activity, GalleryDetailActivity::class.java).apply {
                putExtra(EXTRA_URL, currentUriPath)
                putExtra(EXTRA_TRANSITION_NAME, transitionName)
            }
            activity.startActivity(intent, options.toBundle())
        }
    }
}