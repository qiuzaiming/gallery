package com.zaiming.android.gallery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.zaiming.android.gallery.adapter.AlbumsAdapter
import com.zaiming.android.gallery.bean.AlbumAsset
import com.zaiming.android.gallery.databinding.FragmentAlbumsBinding
import com.zaiming.android.gallery.extensions.repeatOnLifecycleOnStart
import com.zaiming.android.gallery.utils.windowInsets.applySystemBarImmersionMode
import com.zaiming.android.gallery.viewmodel.GalleryViewModel
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
        binding.recyclerviewAlbum.applySystemBarImmersionMode()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

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

    companion object {
        private const val spanCount = 3
    }
}
