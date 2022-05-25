package com.zaiming.android.gallery.ui.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import com.bumptech.glide.Glide
import com.zaiming.android.gallery.base.BaseActivity
import com.zaiming.android.gallery.databinding.ActivityGalleryDetailBinding
import com.zaiming.android.gallery.extensions.applyImmersionWithWindowInsets
import com.zaiming.android.gallery.extensions.applyMaterialTransform

/**
 * @author zaiming
 */
class GalleryDetailActivity : BaseActivity<ActivityGalleryDetailBinding>() {

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
        applyImmersionWithWindowInsets()

        Glide.with(this).load(galleryDetailUri).into(binding.ivDetail)

        binding.ivDetail.doOnPreDraw {
            startPostponedEnterTransition()
        }
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
