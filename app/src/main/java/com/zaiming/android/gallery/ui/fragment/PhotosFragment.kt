package com.zaiming.android.gallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.adapter.PhotosAdapter
import com.zaiming.android.gallery.animator.SpringAddItemAnimator
import com.zaiming.android.gallery.databinding.FragmentPhotosBinding
import com.zaiming.android.gallery.extensions.repeatOnLifecycleOnStart
import com.zaiming.android.gallery.utils.windowInsets.doOnApplyWindowInsets
import com.zaiming.android.gallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author zaiming
 */
@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    private val galleryViewModel: GalleryViewModel by activityViewModels()

    private val photosAdapter by lazy {
        PhotosAdapter(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        settingWindowInsetsParams()
        setAnimator()
        return binding.root
    }

    private fun setAnimator() {
        binding.vfPhotos.setInAnimation(requireContext(), R.anim.anim_fade_in)
        binding.vfPhotos.setOutAnimation(requireContext(), R.anim.anim_fade_out)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridLayoutManager = GridLayoutManager(requireContext(), 4).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (photosAdapter.isHeaderForPosition(position)) 4 else 1
                }
            }
        }
        binding.rvPhotos.apply {
            itemAnimator = SpringAddItemAnimator()
            layoutManager = gridLayoutManager
            adapter = photosAdapter
        }

        repeatOnLifecycleOnStart {
            galleryViewModel.asMediaStoreFlow().collect {
                photosAdapter.setSections(it)
                binding.vfPhotos.displayedChild = 1
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun settingWindowInsetsParams() {
        binding.rvPhotos.doOnApplyWindowInsets { view, windowInsetsCompat, _, _ ->
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