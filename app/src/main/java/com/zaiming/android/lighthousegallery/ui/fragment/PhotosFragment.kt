package com.zaiming.android.lighthousegallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.zaiming.android.lighthousegallery.GalleryApplication
import com.zaiming.android.lighthousegallery.adapter.PhotosAdapter
import com.zaiming.android.lighthousegallery.adapter.SectionedSpanSizeLookup
import com.zaiming.android.lighthousegallery.databinding.FragmentPhotosBinding
import com.zaiming.android.lighthousegallery.extensions.customViewModel
import com.zaiming.android.lighthousegallery.extensions.imageContentUri
import com.zaiming.android.lighthousegallery.mediastore.MediaStoreCollection
import com.zaiming.android.lighthousegallery.viewmodel.PhotosViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * @author zaiming
 */
class PhotosFragment : Fragment() {

    private var _binding: FragmentPhotosBinding? = null
    private val binding get() = _binding!!
    private val photosViewModel by customViewModel {
        PhotosViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotosBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotosAdapter(requireContext(), null)
        val gridLayoutManager = GridLayoutManager(requireContext(), 4).apply {
            spanSizeLookup = SectionedSpanSizeLookup(adapter, this)
        }
        binding.rvPhotos.layoutManager = gridLayoutManager
        binding.rvPhotos.adapter = adapter

        lifecycleScope.launch {
            val fetchMediaContents = MediaStoreCollection.fetchMediaContents(context = GalleryApplication.instance, contentUri = imageContentUri())
            photosViewModel.emitMediaStoreGroup(fetchMediaContents)

            photosViewModel.asMediaStoreFlow().collect {
                Timber.e("this data is $it")
                adapter.setData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}