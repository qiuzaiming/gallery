package com.zaiming.android.lighthousegallery.adapter.viewholders

import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.text.TextUtils
import android.view.View
import com.zaiming.android.lighthousegallery.R

/**
 * @author zaiming
 */
class CountHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvTitleText: TextView = itemView.findViewById(R.id.header_title)

    fun setTitle(text: String?) {
        if (text?.isNotBlank() == true) {
            tvTitleText.text = text
        }
    }
}