package com.zaiming.android.gallery.adapter.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zaiming.android.gallery.adapter.PhotosAdapter

/**
 * PhotosAdapter divider itemDecoration
 */
class PhotosItemDecoration(private val photosAdapter: PhotosAdapter) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        var spanCount = 1
        if (parent.layoutManager is GridLayoutManager) {
            spanCount = (parent.layoutManager as GridLayoutManager).spanCount
        }
        if (!photosAdapter.isHeaderForPosition(position)) {
            val index = photosAdapter.getIndexOfPosition(position)
            val column = index % spanCount
            outRect.left = column
            outRect.right = spanCount - column - 1
            outRect.top = spanCount - 1
            outRect.bottom = 0
            outRect.multi(2)
        }
    }
}

fun Rect.multi(multi: Int) {
    this.left *= multi
    this.top *= multi
    this.right *= multi
    this.bottom *= multi
}
