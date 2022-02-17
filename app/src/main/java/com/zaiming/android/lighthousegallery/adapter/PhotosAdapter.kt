package com.zaiming.android.lighthousegallery.adapter

import android.view.ViewGroup
import com.zaiming.android.lighthousegallery.adapter.viewholders.CountFootViewHolder
import com.zaiming.android.lighthousegallery.adapter.viewholders.CountHeaderViewHolder
import com.zaiming.android.lighthousegallery.adapter.viewholders.CountItemViewHolder
import com.zaiming.android.lighthousegallery.bean.Asset

/**
 * @author zaiming
 */
class PhotosAdapter: SectionsDiffAdapter<String, Asset, Any,
        Long,
        CountHeaderViewHolder, CountItemViewHolder, CountFootViewHolder>() {

    override fun onCreateSectionHeaderViewHolder(parent: ViewGroup): CountHeaderViewHolder {
        return CountHeaderViewHolder(parent)
    }

    override fun onCreateSectionFooterViewHolder(parent: ViewGroup): CountFootViewHolder {
        return CountFootViewHolder(parent)
    }

    override fun onCreateSectionItemViewHolder(parent: ViewGroup): CountItemViewHolder {
        return CountItemViewHolder(parent)
    }

    override fun onBindSectionHeaderViewHolder(viewHolder: CountHeaderViewHolder, section: Int) {
        viewHolder.bindTo(currentSections[section].header)
    }

    override fun onBindSectionFooterViewHolder(viewHolder: CountFootViewHolder, section: Int) {
        TODO("Not yet implemented")
    }

    override fun onBindSectionItemViewHolder(
        viewHolder: CountItemViewHolder,
        section: Int,
        index: Int,
        payloads: MutableList<Any>
    ) {
        val assets = currentSections[section].data[index]
        val selectable = isSelectableStatus()
        val selected = isSelected(getPositionOf(section, index))
        viewHolder.render(assets)
    }

    override fun isSectionHeaderTheSame(header1: String, header2: String): Boolean {
        return header1 == header2
    }

    override fun isSectionHeaderContentTheSame(header1: String, header2: String): Boolean {
        return header1 == header2
    }

    override fun isSectionItemTheSame(item1: Asset, item2: Asset): Boolean {
        return item1.id == item2.id
    }

    override fun isSectionItemContentTheSame(item1: Asset, item2: Asset): Boolean {
        return item1 == item2
    }

    override fun isSectionFooterTheSame(footer1: Any, footer2: Any): Boolean {
        return false
    }

    override fun isSectionFooterContentTheSame(footer1: Any, footer2: Any): Boolean {
        return false
    }

    override fun generateId(item: Asset): Long = item.id

}