package com.zaiming.android.gallery.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.zaiming.android.gallery.R
import com.zaiming.android.gallery.bean.AlbumAsset
import com.zaiming.android.gallery.galleryinterface.ItemClickListener

/**
 * @author zaiming
 */
class AlbumsAdapter(private val items: MutableList<AlbumAsset>) : RecyclerView.Adapter<AlbumsAdapter.ViewHolder>() {

    private var onClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val albumViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false))
        onClickListener?.let {
            albumViewHolder.albumContainer.setOnClickListener {
                val adapterPosition = albumViewHolder.adapterPosition
                if (adapterPosition == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                setOnItemClick(it, adapterPosition)
            }
        }
        return albumViewHolder
    }

    private fun setOnItemClick(targetView: View, position: Int) {
        onClickListener?.onItemClick(targetView, position)
    }

    fun setOnClickListener(clickListener: ItemClickListener) {
        this.onClickListener = clickListener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo(items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val albumCover: AppCompatImageView = itemView.findViewById(R.id.album_cover)
        private val albumName: AppCompatTextView = itemView.findViewById(R.id.tv_album_name)
        private val albumCount: AppCompatTextView = itemView.findViewById(R.id.tv_album_count)
        val albumContainer: LinearLayout = itemView.findViewById(R.id.album_container)

        fun bindTo(albumAsset: AlbumAsset) {
            Glide.with(albumCover.context).load(albumAsset.cover).into(albumCover)
            albumName.text = albumAsset.albumName
            albumCount.text = albumAsset.count.toString()
        }
    }
}
