package com.zaiming.android.gallery.adapter.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zaiming.android.gallery.R

/**
 * @author zaiming
 */
class CountHeaderViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.view_count_header, parent, false)
) {
    private val tvTitleText: TextView = itemView.findViewById(R.id.header_title)

    fun bindTo(text: String?) {
        if (text?.isNotBlank() == true) {
            tvTitleText.text = text
        }
    }
}
