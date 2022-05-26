package com.zaiming.android.gallery.ui.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.adapter.AlbumsAdapter
import com.zaiming.android.gallery.animator.SpringAddItemAnimator
import com.zaiming.android.gallery.base.BaseControllerFragment
import com.zaiming.android.gallery.base.BaseFragment
import com.zaiming.android.gallery.bean.AlbumAsset
import com.zaiming.android.gallery.databinding.FragmentAlbumsBinding
import com.zaiming.android.gallery.extensions.*
import com.zaiming.android.gallery.galleryinterface.ItemClickListener
import com.zaiming.android.gallery.utils.windowInsets.applySystemBarImmersionMode
import com.zaiming.android.gallery.viewmodel.GalleryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author zaiming
 */
@AndroidEntryPoint
class AlbumsFragment : BaseControllerFragment<FragmentAlbumsBinding>() {

    private var albumAssetGroup: MutableList<AlbumAsset> = ArrayList()
    private val albumAdapter by lazy {
        AlbumsAdapter(albumAssetGroup)
    }

    override fun init() {

        binding.recyclerviewAlbum.applySystemBarImmersionMode()

        initView()

        lifecycleScope.launchWhenStarted {
            galleryViewModel.asAlbumMediaStoreFlow().collect {
                albumAssetGroup.clear()
                albumAssetGroup.addAll(it.toMutableList())
                albumAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initView() {

        albumAdapter.apply {
            setHasStableIds(true)
            setOnClickListener(object : ItemClickListener {
                override fun onItemClick(view: View, position: Int) {
                }
            })
        }

        binding.apply {

            recyclerviewAlbum.apply {
                layoutManager = GridLayoutManager(requireContext(), spanCount)
                itemAnimator = SpringAddItemAnimator()
                adapter = albumAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        when(newState) {
                            RecyclerView.SCROLL_STATE_DRAGGING -> fabAddAlbum.hide()
                            RecyclerView.SCROLL_STATE_IDLE -> fabAddAlbum.show()
                        }
                    }
                })
            }

            fabAddAlbum.apply {
                setShowMotionSpecResource(R.animator.fab_show)
                setHideMotionSpecResource(R.animator.fab_hide)

                setOnSingleClick {
                    applyMaterialContainerTransitionBetweenTwoViews(binding.root, it, cardviewAlbumDetail)
                    cardviewAlbumDetail.beVisible()
                    fabAddAlbum.beGone()
                }
            }


            cardviewAlbumDetail.setOnSingleClick {
                applyMaterialContainerTransitionBetweenTwoViews(binding.root, it, fabAddAlbum)
                cardviewAlbumDetail.beGone()
                fabAddAlbum.beVisible()
            }
        }
    }


    override fun scrollToTop() {
    }

    companion object {
        private const val spanCount = 3
    }
}
