package com.zaiming.android.gallery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

// 可多选的类型对应的 id 类型，比如数据库中的自增 id
abstract class BaseAdapter<ID> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        protected const val VIEW_TYPE = 1
    }

    /**
     * 是否为可选状态
     */
    private var selectable: Boolean = false

    /**
     * 已经选择的列表的 id
     */
    var selectedItemIds: MutableSet<ID> = HashSet()

    /**
     * 如果有多个 item 类型，可以继承这个方法
     */
    protected open fun getGalleryItemViewType(position: Int): Int {
        return VIEW_TYPE
    }

    // 透传
    protected abstract fun onCreateGalleryViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder

    // 透传
    protected abstract fun onBindGalleryViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>)

    // 支持多选
    open fun isSelectableStatus() = selectable

    open fun isSelected(position: Int) = selectedItemIds.contains(generateId(position))

    protected fun isSelected(id: ID) = selectedItemIds.contains(id)

    /**
     * 设置进入或者退出多选状态
     */
    fun setSelectableStatus(selectable: Boolean) {
        this.selectable = selectable
        selectedItemIds = HashSet()
        notifySelectItemsChanged()
    }

    fun selectOne(position: Int, select: Boolean) {
        if (select) {
            selectOne(position)
        } else {
            unSelectOne(position)
        }
    }

    /**
     * 选择一项
     */
    private fun selectOne(position: Int) {
        generateId(position)?.let {
            selectedItemIds.add(it)
            notifySelectItemChange(position)
        }
    }

    /**
     * 不选择一项
     */
    private fun unSelectOne(position: Int) {
        selectedItemIds.remove(generateId(position))
        notifySelectItemChange(position)
    }

    /**
     * 全选
     */
    fun selectAll() {
        for (position in firstIndexOfPosition() until lastIndexOfPosition()) {
            generateId(position)?.let {
                selectedItemIds.add(it)
            }
        }
        notifySelectItemsChanged()
    }

    private fun selectRange(start: Int, end: Int) {

        for (position in start..end) {
            generateId(position)?.let {
                selectedItemIds.add(it)
            }
        }
        notifyItemRangeChanged(start, end - start + 1)
    }

    private fun unSelectRange(start: Int, end: Int) {
        for (position in start..end) {
            generateId(position)?.let {
                selectedItemIds.remove(it)
            }
        }
        notifyItemRangeChanged(start, end - start + 1)
    }

    fun selectRange(start: Int, end: Int, select: Boolean) {
        if (select) selectRange(start, end) else unSelectRange(start, end)
    }

    /**
     * 全不选
     */
    fun unSelectAll() {
        selectedItemIds.clear()
        notifySelectItemsChanged()
    }

    fun getSelectedItemCount(): Int {
        return selectedItemIds.size
    }

    protected abstract fun generateId(position: Int): ID?

    override fun getItemViewType(position: Int): Int {
        return getGalleryItemViewType(position)
    }

    private fun notifySelectItemsChanged() {
        notifyItemRangeChanged(firstIndexOfPosition(), lastIndexOfPosition(), SelectPayload())
    }

    private fun notifySelectItemChange(position: Int) {
        notifyItemChanged(position, SelectPayload())
    }

    protected fun index2Position(index: Int): Int {
        return index
    }

    protected fun position2Index(position: Int): Int {
        return position
    }

    private fun firstIndexOfPosition(): Int {
        return index2Position(0)
    }

    private fun lastIndexOfPosition(): Int {
        return itemCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return onCreateGalleryViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        this.onBindViewHolder(holder, position, mutableListOf())
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        onBindGalleryViewHolder(holder, position, payloads)
    }

    protected fun isIndexPosition(position: Int): Boolean {
        return position < itemCount
    }

    class SelectPayload
}
