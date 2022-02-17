package com.zaiming.android.lighthousegallery.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.zaiming.android.lighthousegallery.adapter.SectionedRecyclerViewAdapter
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.zaiming.android.lighthousegallery.adapter.SectionedRecyclerViewAdapter.SectionDataObserver

/**
 * source code from https://github.com/luizgrp/SectionedRecyclerViewAdapter
 */
@Deprecated(message = "use SectionsDiffAdapter to replace SectionedRecyclerViewAdapter")
abstract class SectionedRecyclerViewAdapter<H : RecyclerView.ViewHolder, VH : RecyclerView.ViewHolder, F : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var sectionForPosition: IntArray? = null
    private var positionWithinSection: IntArray? = null
    private var isHeader: BooleanArray? = null
    private var isFooter: BooleanArray? = null
    private var count = 0
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        setupIndices()
    }

    /**
     * Returns the sum of number of items for each section plus headers and footers if they
     * are provided.
     */
    override fun getItemCount(): Int {
        return count
    }

    private fun setupIndices() {
        count = countItems()
        allocateAuxiliaryArrays(count)
        precomputeIndices()
    }

    private fun countItems(): Int {
        var count = 0
        val sections = sectionCount
        for (i in 0 until sections) {
            count += 1 + getItemCountForSection(i) + if (hasFooterInSection(i)) 1 else 0
        }
        return count
    }

    private fun precomputeIndices() {
        val sections = sectionCount
        var index = 0
        for (i in 0 until sections) {
            setPrecomputedItem(index, true, false, i, 0)
            index++
            for (j in 0 until getItemCountForSection(i)) {
                setPrecomputedItem(index, false, false, i, j)
                index++
            }
            if (hasFooterInSection(i)) {
                setPrecomputedItem(index, false, true, i, 0)
                index++
            }
        }
    }

    private fun allocateAuxiliaryArrays(count: Int) {
        sectionForPosition = IntArray(count)
        positionWithinSection = IntArray(count)
        isHeader = BooleanArray(count)
        isFooter = BooleanArray(count)
    }

    private fun setPrecomputedItem(
        index: Int,
        isHeader: Boolean,
        isFooter: Boolean,
        section: Int,
        position: Int
    ) {
        this.isHeader!![index] = isHeader
        this.isFooter!![index] = isFooter
        sectionForPosition!![index] = section
        positionWithinSection!![index] = position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder = when {
            isSectionHeaderViewType(viewType) -> {
                onCreateSectionHeaderViewHolder(parent, viewType)
            }
            isSectionFooterViewType(viewType) -> {
                onCreateSectionFooterViewHolder(parent, viewType) as RecyclerView.ViewHolder
            }
            else -> {
                onCreateItemViewHolder(parent, viewType)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val section = sectionForPosition!![position]
        val index = positionWithinSection!![position]
        when {
            isSectionHeaderPosition(position) -> {
                onBindSectionHeaderViewHolder(holder as H, section)
            }
            isSectionFooterPosition(position) -> {
                onBindSectionFooterViewHolder(holder as F, section)
            }
            else -> {
                onBindItemViewHolder(holder as VH, section, index)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (sectionForPosition == null) {
            setupIndices()
        }
        return when {
            isSectionHeaderPosition(position) -> {
                getSectionHeaderViewType()
            }
            isSectionFooterPosition(position) -> {
                getSectionFooterViewType()
            }
            else -> {
                getSectionItemViewType()
            }
        }
    }

    private fun getSectionHeaderViewType(): Int {
        return TYPE_SECTION_HEADER
    }

    private fun getSectionFooterViewType(): Int {
        return TYPE_SECTION_FOOTER
    }

    private fun getSectionItemViewType(): Int {
        return TYPE_ITEM
    }

    /**
     * Returns true if the argument position corresponds to a header
     */
    fun isSectionHeaderPosition(position: Int): Boolean {
        if (isHeader == null) {
            setupIndices()
        }
        return isHeader!![position]
    }

    /**
     * Returns true if the argument position corresponds to a footer
     */
    fun isSectionFooterPosition(position: Int): Boolean {
        if (isFooter == null) {
            setupIndices()
        }
        return isFooter!![position]
    }

    protected fun isSectionHeaderViewType(viewType: Int): Boolean {
        return viewType == TYPE_SECTION_HEADER
    }

    protected fun isSectionFooterViewType(viewType: Int): Boolean {
        return viewType == TYPE_SECTION_FOOTER
    }

    /**
     * Returns the number of sections in the RecyclerView
     */
    protected abstract val sectionCount: Int

    /**
     * Returns the number of items for a given section
     */
    protected abstract fun getItemCountForSection(section: Int): Int

    /**
     * Returns true if a given section should have a footer
     */
    protected abstract fun hasFooterInSection(section: Int): Boolean

    /**
     * Creates a ViewHolder of class H for a Header
     */
    protected abstract fun onCreateSectionHeaderViewHolder(parent: ViewGroup?, viewType: Int): H

    /**
     * Creates a ViewHolder of class F for a Footer
     */
    protected abstract fun onCreateSectionFooterViewHolder(parent: ViewGroup, viewType: Int): F?

    /**
     * Creates a ViewHolder of class VH for an Item
     */
    protected abstract fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): VH

    /**
     * Binds data to the header view of a given section
     */
    protected abstract fun onBindSectionHeaderViewHolder(holder: H, section: Int)

    /**
     * Binds data to the footer view of a given section
     */
    protected abstract fun onBindSectionFooterViewHolder(holder: F, section: Int)

    /**
     * Binds data to the item view for a given position within a section
     */
    protected abstract fun onBindItemViewHolder(holder: VH, section: Int, position: Int)
    internal inner class SectionDataObserver : AdapterDataObserver() {
        override fun onChanged() {
            setupIndices()
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
            setupIndices()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            setupIndices()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            setupIndices()
        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
            setupIndices()
        }
    }

    companion object {
        protected const val TYPE_SECTION_HEADER = -1
        protected const val TYPE_SECTION_FOOTER = -2
        protected const val TYPE_ITEM = -3
    }

    init {
        registerAdapterDataObserver(SectionDataObserver())
    }
}