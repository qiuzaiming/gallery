package com.zaiming.android.lighthousegallery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.stream.IntStream.range

abstract class SectionsAdapter<
        ID, // section item 的 id 类型
        VH : RecyclerView.ViewHolder, // section header 的 ViewHolder
        VI : RecyclerView.ViewHolder, // section item 的 ViewHolder
        VF : RecyclerView.ViewHolder  // section footer 的 ViewHolder
        >
    : BaseAdapter<ID>() {

    companion object {
        protected const val TYPE_SECTION_HEADER = 101
        protected const val TYPE_SECTION_FOOTER = 102
        protected const val TYPE_SECTION_ITEM = 103
    }

    protected var sectionForPosition = IntArray(0)
    protected var positionForPosition = IntArray(0)
    protected var isHeaderForPosition = BooleanArray(0)
    protected var isFooterForPosition = BooleanArray(0)


    private var count = 0

    protected abstract fun onCreateSectionHeaderViewHolder(parent: ViewGroup) : VH

    protected abstract fun onCreateSectionFooterViewHolder(parent: ViewGroup) : VF

    protected abstract fun onCreateSectionItemViewHolder(parent: ViewGroup) : VI

    protected abstract fun onBindSectionHeaderViewHolder(viewHolder: VH, section: Int)

    protected abstract fun onBindSectionFooterViewHolder(viewHolder: VF, section: Int)

    protected abstract fun onBindSectionItemViewHolder(viewHolder: VI, section: Int, index: Int, payloads: MutableList<Any>)

    protected abstract fun getSectionCount() : Int

    /**
     * 指定 section 中项目的个数，不包括 header 和 footer
     */
    protected abstract fun getSectionItemCount(section: Int) : Int

    override fun getItemCount(): Int = count

    fun computeIndices(sectionCount: Int, itemCount: (Int)-> Int) : Array<Any> {

        val totalCount = calculateTotalCount(sectionCount, itemCount)
        val sectionForPosition = IntArray(totalCount)
        val positionForPosition = IntArray(totalCount)
        val isHeaderForPosition = BooleanArray(totalCount)
        val isFooterForPosition = BooleanArray(totalCount)

        var index = 0
        for (section in range(0, sectionCount)) {
            assignPositionForSections(sectionForPosition, positionForPosition, isHeaderForPosition, isFooterForPosition,
                index, true, false, section, -1)
            index++
            for (position in range(0, itemCount(section))) {
                assignPositionForSections(sectionForPosition, positionForPosition, isHeaderForPosition, isFooterForPosition,
                    index, false, false, section, position)
                index++
            }
        }
        return arrayOf(sectionForPosition, positionForPosition, isHeaderForPosition, isFooterForPosition)
    }

    fun refreshIndices(totalCount: Int, sectionForPosition: IntArray, positionForPosition: IntArray,
                       isHeaderForPosition: BooleanArray, isFooterForPosition: BooleanArray,) {
        count = totalCount
        this.sectionForPosition = sectionForPosition
        this.positionForPosition = positionForPosition
        this.isHeaderForPosition = isHeaderForPosition
        this.isFooterForPosition = isFooterForPosition
    }

    fun getIndexOfPosition(position: Int) : Int {

        return positionForPosition.getOrElse(position) {
            -1
        }
    }

    private fun assignPositionForSections(sectionForPosition: IntArray, positionForPosition: IntArray,
                                          isHeaderForPosition: BooleanArray, isFooterForPosition: BooleanArray,
                                          index : Int, isHeader : Boolean, isFooter : Boolean,
                                          section: Int, position: Int) {

        sectionForPosition[index] = section
        positionForPosition[index] = position
        isHeaderForPosition[index] = isHeader
        isFooterForPosition[index] = isFooter
    }

    fun isHeaderForPosition(position: Int) : Boolean {
        return isHeaderForPosition.getOrElse(position) {
            false
        }
    }


    fun getTotalItemCount() : Int {

        var totalCount = 0
        for (section in 0 until getSectionCount()) {
            totalCount += getSectionItemCount(section)
        }
        return totalCount
    }


    fun getPositionOf(section: Int, position: Int) : Int {

        var index = 0
        for (sectionIndex in 0 until section) {
            val decorCount = 1
            index += getSectionItemCount(sectionIndex) + decorCount
        }
        return index2Position(index + position + 1)
    }

    protected fun calculateTotalCount() : Int {
        return calculateTotalCount(getSectionCount()) {
            getSectionItemCount(it)
        }
    }

    fun calculateTotalCount(sectionCount: Int, itemCount: (Int) -> Int): Int {

        var totalCount = 0
        for (section in range(0, sectionCount)) {
            totalCount += itemCount(section)
            totalCount += 1
        }
        return totalCount
    }

    fun getItemIndexOf(section: Int, position: Int) : Int {
        var index = 0
        for (i in 0 until section) {
            index += getSectionItemCount(i)
        }
        index += position
        return index2Position(index)
    }

    override fun getGalleryItemViewType(position: Int): Int {
        return when {
            isHeaderForPosition[position] -> TYPE_SECTION_HEADER
            isFooterForPosition[position] -> TYPE_SECTION_FOOTER
            else -> TYPE_SECTION_ITEM
        }
    }

    override fun onCreateGalleryViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {

        return when {
            isSectionHeaderType(type) -> {
                onCreateSectionHeaderViewHolder(parent)
            }
            isSectionFooterType(type) -> {
                onCreateSectionFooterViewHolder(parent)
            }
            else -> {
                onCreateSectionItemViewHolder(parent)
            }
        }
    }


    override fun onBindGalleryViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        val section = sectionForPosition.getOrNull(position)
        val index = positionForPosition.getOrNull(position)

        if (section != null && index != null) {
            when {
                isSectionHeaderPosition(position) -> {
                    onBindSectionHeaderViewHolder(viewHolder as VH, section)
                }
                isSectionFooterPosition(position) -> {
                    onBindSectionFooterViewHolder(viewHolder as VF, section)
                }
                else -> {
                    onBindSectionItemViewHolder(viewHolder as VI, section, index, payloads)
                }
            }
        }
    }


    private fun isSectionItemType(type: Int) : Boolean {
        return type == TYPE_SECTION_ITEM
    }

    private fun isSectionHeaderType(type: Int) : Boolean {
        return type == TYPE_SECTION_HEADER
    }

    private fun isSectionFooterType(type: Int) : Boolean {
        return type == TYPE_SECTION_FOOTER
    }

    private fun isSectionHeaderPosition(position: Int) : Boolean {
        return isHeaderForPosition.size > position && isHeaderForPosition.getOrElse(position) {
            false
        }
    }

    private fun isSectionFooterPosition(position: Int) : Boolean {
        return isFooterForPosition.size > position && isFooterForPosition.getOrElse(position) {
            false
        }
    }


    data class Section<H, T, F>(
        val header : H,
        val data : List<T>,
        val footer : F
    )

}