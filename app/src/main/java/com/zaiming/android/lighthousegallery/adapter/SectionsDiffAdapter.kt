package com.zaiming.android.lighthousegallery.adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

abstract class SectionsDiffAdapter<
        SH, I, SF,
        ID, // section item 的 id 类型
        VH : RecyclerView.ViewHolder, // section header 的 ViewHolder
        VI : RecyclerView.ViewHolder, // section item 的 ViewHolder
        VF : RecyclerView.ViewHolder  // section footer 的 ViewHolder
        > : SectionsAdapter<ID, VH, VI, VF>() {

    protected var currentSections : List<Section<SH, I, SF>> = ArrayList()

    suspend fun setSections(newSections : List<Section<SH, I, SF>>) {
        withContext(Dispatchers.Main) {
            val diffSectionResult = withContext(Dispatchers.IO) {
                diffSections(newSections)
            }
            refreshIndices(diffSectionResult.totalCount, diffSectionResult.sectionForPosition,
                diffSectionResult.positionForPosition, diffSectionResult.isHeaderForPosition,
                diffSectionResult.isFooterForPosition)
            currentSections = newSections
            diffSectionResult.diffResult.dispatchUpdatesTo(this@SectionsDiffAdapter)
        }
    }

    fun setSectionsInMainThread(newSections : List<Section<SH, I, SF>>) {
        val diffSectionResult = diffSections(newSections)
        refreshIndices(diffSectionResult.totalCount, diffSectionResult.sectionForPosition,
            diffSectionResult.positionForPosition, diffSectionResult.isHeaderForPosition,
            diffSectionResult.isFooterForPosition)
        currentSections = newSections
        diffSectionResult.diffResult.dispatchUpdatesTo(this@SectionsDiffAdapter)
    }

    fun getSelectedItems() : List<I> {
        val sections = currentSections
        val selectedItems = LinkedList<I>()
        for (section in sections) {
            for (item in section.data) {
                if (isSelected(generateId(item))) {
                    selectedItems.add(item)
                }
            }
        }
        return selectedItems
    }



    abstract fun isSectionHeaderTheSame(header1: SH, header2: SH): Boolean

    abstract fun isSectionHeaderContentTheSame(header1: SH, header2: SH): Boolean

    abstract fun isSectionItemTheSame(item1: I, item2: I): Boolean

    abstract fun isSectionItemContentTheSame(item1: I, item2: I): Boolean

    abstract fun isSectionFooterTheSame(footer1: SF, footer2: SF): Boolean

    abstract fun isSectionFooterContentTheSame(footer1: SF, footer2: SF): Boolean


    abstract fun generateId(item : I) : ID


    override fun getSectionCount(): Int {
        return currentSections.size
    }

    override fun getSectionItemCount(section: Int): Int {

        val sectionItem = currentSections.getOrNull(section)
        return sectionItem?.data?.size ?: 0
    }


    override fun generateId(position: Int): ID? {
        val section = sectionForPosition.getOrNull(position)
        val index = positionForPosition.getOrNull(position)

        return if (section != null && index != null) {
            val item = currentSections.getOrNull(section)?.data?.getOrNull(index)
            return item?.let {
                generateId(item)
            }
        } else {
            null
        }
    }

    private fun diffSections(newSections: List<Section<SH, I, SF>>) : DiffSectionResult {

        val oldSections = currentSections
        val oldTotalCount = calculateTotalCount()
        val oldSectionForPosition = sectionForPosition
        val oldPositionForPosition = positionForPosition
        val oldIsHeaderForPosition = isHeaderForPosition
        val oldIsFooterForPosition = isFooterForPosition

        val sectionCount = newSections.size
        val itemCountCalculator: (Int) -> Int = {
            newSections[it].data.size
        }
        val newTotalCount = calculateTotalCount(sectionCount, itemCountCalculator)
        val positions = computeIndices(sectionCount, itemCountCalculator)
        val newSectionForPosition: IntArray = positions[0] as IntArray
        val newPositionForPosition: IntArray = positions[1] as IntArray
        val newIsHeaderForPosition: BooleanArray = positions[2] as BooleanArray
        val newIsFooterForPosition: BooleanArray = positions[3] as BooleanArray

        val diffResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

                if (oldItemPosition >= oldSectionForPosition.size ||
                    newItemPosition >= newSectionForPosition.size
                ) {
                    return false
                }

                val oldSectionIndex = oldSectionForPosition[oldItemPosition]
                val oldPosition = oldPositionForPosition[oldItemPosition]
                val oldIsHeader = oldIsHeaderForPosition[oldItemPosition]
                val oldIsFooter = oldIsFooterForPosition[oldItemPosition]

                val newSectionIndex = newSectionForPosition[newItemPosition]
                val newPosition = newPositionForPosition[newItemPosition]
                val newIsHeader = newIsHeaderForPosition[newItemPosition]
                val newIsFooter = newIsFooterForPosition[newItemPosition]

                val oldSection = oldSections.getOrNull(oldSectionIndex)
                val newSection = newSections.getOrNull(newSectionIndex)

                if (oldSection != null && newSection != null && oldSection.data.size > oldPosition &&
                    newSection.data.size > newPosition
                ) {

                    if (oldIsHeader && newIsHeader) {
                        return isSectionHeaderTheSame(
                            oldSections[oldSectionIndex].header,
                            newSections[newSectionIndex].header
                        )
                    } else if (oldIsFooter && newIsFooter) {
                        return isSectionFooterTheSame(
                            oldSections[oldSectionIndex].footer,
                            newSections[newSectionIndex].footer
                        )
                    } else if (oldPosition >= 0 && newPosition >= 0) {
                        return isSectionItemTheSame(
                            oldSections[oldSectionIndex].data[oldPosition],
                            newSections[newSectionIndex].data[newPosition]
                        )
                    }
                } else {
                    return false
                }

                return false
            }

            override fun getOldListSize(): Int {
                return oldTotalCount
            }

            override fun getNewListSize(): Int {
                return newTotalCount
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {

                if (oldItemPosition >= oldSectionForPosition.size ||
                    newItemPosition >= newSectionForPosition.size
                ) {
                    return false
                }

                val oldSectionIndex = oldSectionForPosition[oldItemPosition]
                val oldPosition = oldPositionForPosition[oldItemPosition]
                val oldIsHeader = oldIsHeaderForPosition[oldItemPosition]
                val oldIsFooter = oldIsFooterForPosition[oldItemPosition]

                val newSectionIndex = newSectionForPosition[newItemPosition]
                val newPosition = newPositionForPosition[newItemPosition]
                val newIsHeader = newIsHeaderForPosition[newItemPosition]
                val newIsFooter = newIsFooterForPosition[newItemPosition]

                val oldSection = oldSections.getOrNull(oldSectionIndex)
                val newSection = newSections.getOrNull(newSectionIndex)

                if (oldSection != null && newSection != null && oldSection.data.size > oldPosition &&
                    newSection.data.size > newPosition
                ) {

                    if (oldIsHeader && newIsHeader) {
                        return isSectionHeaderContentTheSame(
                            oldSections[oldSectionIndex].header,
                            newSections[newSectionIndex].header
                        )
                    } else if (oldIsFooter && newIsFooter) {
                        return isSectionFooterContentTheSame(
                            oldSections[oldSectionIndex].footer,
                            newSections[newSectionIndex].footer
                        )
                    } else if (oldSectionIndex == newSectionIndex && oldPosition == newPosition // 在 bindViewHolder 时，同时也会绑定位置信息，并在回调函数中返回，
                        // 因此，这里也比较了位置信息。
                        // 后续可以考虑不将位置信息和 viewHolder 绑定
                        && oldPosition >= 0 && newPosition >= 0
                    ) {
                        return isSectionItemContentTheSame(
                            oldSections[oldSectionIndex].data[oldPosition],
                            newSections[newSectionIndex].data[newPosition]
                        )
                    }
                } else {
                    return false
                }

                return false
            }
        }, false)

        return  DiffSectionResult(diffResult, newTotalCount, newSectionForPosition, newPositionForPosition,
            newIsHeaderForPosition, newIsFooterForPosition)
    }

    data class DiffSectionResult(val diffResult: DiffUtil.DiffResult, val totalCount: Int,
                                 val sectionForPosition: IntArray,
                                 val positionForPosition: IntArray,
                                 val isHeaderForPosition: BooleanArray,
                                 val isFooterForPosition: BooleanArray)
}