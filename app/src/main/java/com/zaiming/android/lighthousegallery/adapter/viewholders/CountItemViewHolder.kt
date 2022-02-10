package com.zaiming.android.lighthousegallery.adapter.viewholders

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.zaiming.android.lighthousegallery.bean.Asset
import com.bumptech.glide.Glide
import com.zaiming.android.lighthousegallery.R

/**
 * @author zaiming
 */
class CountItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var ivPic: ImageView = itemView.findViewById(R.id.iv_pic)

    fun render(asset: Asset) {
        Glide.with(ivPic.context)
            .load(asset.uri)
            .into(ivPic)
    }
}