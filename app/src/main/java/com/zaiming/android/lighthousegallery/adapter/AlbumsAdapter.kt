package com.zaiming.android.lighthousegallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaiming.android.lighthousegallery.R
import com.zaiming.android.lighthousegallery.bean.AlbumAsset

/**
 * @author zaiming
 */
class AlbumsAdapter(private val items: MutableList<AlbumAsset>): RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val albumCover: AppCompatImageView = itemView.findViewById(R.id.album_cover)
        private val albumName: AppCompatTextView = itemView.findViewById(R.id.tv_album_name)
        private val albumCount: AppCompatTextView = itemView.findViewById(R.id.tv_album_count)

        fun bindTo(albumAsset: AlbumAsset) {
            Glide.with(albumCover.context).load(albumAsset.cover).into(albumCover)
            albumName.text = albumAsset.albumName
            albumCount.text = albumAsset.count.toString()
        }
    }
}