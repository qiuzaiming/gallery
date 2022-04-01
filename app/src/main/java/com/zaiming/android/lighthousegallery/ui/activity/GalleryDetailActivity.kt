package com.zaiming.android.lighthousegallery.ui.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.bumptech.glide.Glide
import com.zaiming.android.lighthousegallery.databinding.ActivityGalleryDetailBinding
import com.zaiming.android.lighthousegallery.extensions.applyImmersionWithWindowInsets
import com.zaiming.android.lighthousegallery.extensions.applyMaterialTransform

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
