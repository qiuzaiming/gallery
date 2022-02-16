package com.zaiming.android.lighthousegallery.adapter

import android.content.Context
import com.zaiming.android.lighthousegallery.adapter.viewholders.CountHeaderViewHolder
import com.zaiming.android.lighthousegallery.adapter.viewholders.CountItemViewHolder
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.zaiming.android.lighthousegallery.bean.AssetLibrary
import android.view.ViewGroup
import com.zaiming.android.lighthousegallery.R
import com.zaiming.android.lighthousegallery.adapter.viewholders.CountFootViewHolder

/**
 * @author zaiming
 */
class PhotosAdapter(mContext: Context, assetLibraryGroup: List<AssetLibrary> = emptyList()) :
    SectionedRecyclerViewAdapter<CountHeaderViewHolder, CountItemViewHolder, RecyclerView.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var dataGroup = assetLibraryGroup

    override val sectionCount: Int
        get() = dataGroup.size

    override fun getItemCountForSection(section: Int): Int {
        return dataGroup[section].assets.size
    }

    override fun hasFooterInSection(section: Int): Boolean {
        return false
    }

    override fun onCreateSectionHeaderViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): CountHeaderViewHolder {
        val view = mInflater.inflate(R.layout.view_count_header, parent, false)
        return CountHeaderViewHolder(view)
    }

    override fun onCreateSectionFooterViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountFootViewHolder? {
        return null
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): CountItemViewHolder {
        val view = mInflater.inflate(R.layout.item_global_grid, parent, false)
        return CountItemViewHolder(view)
    }

    override fun onBindSectionHeaderViewHolder(
        holder: CountHeaderViewHolder,
        section: Int
    ) {
        if (dataGroup.isEmpty()) {
            return
        }
        holder.setTitle(dataGroup[section].time)
    }

    override fun onBindSectionFooterViewHolder(
        holder: RecyclerView.ViewHolder,
        section: Int
    ) {
    }

    override fun onBindItemViewHolder(
        holder: CountItemViewHolder,
        section: Int,
        position: Int
    ) {
        if (dataGroup.isEmpty()) {
            return
        }
        val currentAsset = dataGroup[section].assets[position]
        holder.render(currentAsset)
    }

    fun setAssetLibrary(newAssetLibrary: List<AssetLibrary>) {
        dataGroup = newAssetLibrary
        notifyDataSetChanged()
    }
}