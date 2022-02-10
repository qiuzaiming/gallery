package com.zaiming.android.lighthousegallery.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup

/**
 * RecyclerView分组的GridLayoutManager.SpanSizeLookup
 */
open class SectionedSpanSizeLookup(
    private var adapter: SectionedRecyclerViewAdapter<*, *, *>,
    private var layoutManager: GridLayoutManager
) : SpanSizeLookup() {
    override fun getSpanSize(position: Int): Int {
        return if (adapter.isSectionHeaderPosition(position)) {
            //是分组头部，该position占用当前行分配的全部空间
            layoutManager.spanCount
        } else {
            //是分组item，该position占用当前行分配的1个空间
            1
        }
    }
}