package com.zaiming.android.gallery.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.bean.Asset

/**
 * @author zaiming
 */
class CountItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_global_grid, parent, false)
) {
    var ivPic: ImageView = itemView.findViewById(R.id.iv_pic)

    fun render(asset: Asset) {
        Glide.with(ivPic.context)
            .load(asset.uri)
            .into(ivPic)
    }
}
