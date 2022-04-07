package com.zaiming.android.lighthousegallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.zaiming.android.lighthousegallery.adapter.AlbumsAdapter
import com.zaiming.android.lighthousegallery.bean.AlbumAsset
import com.zaiming.android.lighthousegallery.databinding.FragmentAlbumsBinding
import com.zaiming.android.lighthousegallery.extensions.repeatOnLifecycleOnStart
import com.zaiming.android.lighthousegallery.utils.windowInsets.doOnApplyWindowInsets
import com.zaiming.android.lighthousegallery.viewmodel.GalleryViewModel
import kotlinx.coroutines.flow.collect

/**
 * @author zaiming
 */
class AlbumsFragment : Fragment() {

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    private val albumsViewModel: GalleryViewModel by activityViewModels()

    private var albumAssetGroup: MutableList<AlbumAsset> = ArrayList()
    private val albumAdapter by lazy {
        AlbumsAdapter(albumAssetGroup)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initRecyclerViewWindowInsets()

        repeatOnLifecycleOnStart {
            albumsViewModel.asAlbumMediaStoreFlow().collect {
                albumAssetGroup.clear()
                albumAssetGroup.addAll(it.toMutableList())
                albumAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {

        albumAdapter.setHasStableIds(true)

        with(binding.recyclerviewAlbum) {
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = albumAdapter
        }
    }

    private fun initRecyclerViewWindowInsets() {
        binding.recyclerviewAlbum.doOnApplyWindowInsets { view, windowInsetsCompat, _, _ ->
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

    companion object {
        private const val spanCount = 3
    }
}
