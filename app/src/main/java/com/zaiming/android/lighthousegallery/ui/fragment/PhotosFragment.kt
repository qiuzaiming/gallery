package com.zaiming.android.lighthousegallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.zaiming.android.lighthousegallery.adapter.PhotosAdapter
import com.zaiming.android.lighthousegallery.animator.SpringAddItemAnimator
import com.zaiming.android.lighthousegallery.databinding.FragmentPhotosBinding
import com.zaiming.android.lighthousegallery.utils.windowInsets.doOnApplyWindowInsets
import com.zaiming.android.lighthousegallery.viewmodel.PhotosViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * @author zaiming
 */
@AndroidEntryPoint
class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!

    private val photosViewModel by lazy {
        ViewModelProvider(requireActivity())[PhotosViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)
        settingWindowInsetsParams()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val photosAdapter = PhotosAdapter()
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

        lifecycleScope.launch {
            photosViewModel.asMediaStoreFlow().collect {
                photosAdapter.setSections(it)
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