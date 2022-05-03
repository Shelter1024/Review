package com.palmtop.app2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.palmtop.app2.model.Singer

/**
 * @author: Shelter
 * Create time: 2022/5/2, 22:32.
 */
class SingerAdapter constructor(val data: List<Singer>) : RecyclerView.Adapter<SingerAdapter.SingerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingerViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_singer_item, parent, false)
        return SingerViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: SingerViewHolder, position: Int) {
        holder.tvName.text = data[position].name
    }

    override fun getItemCount(): Int = data.size

    fun isGroupHead(adapterPosition: Int): Boolean {
        return if (adapterPosition == 0) {
            true
        } else {
            val singer = data[adapterPosition]
            val lastSinger = data[adapterPosition - 1]
            singer.province !== lastSinger.province
        }
    }

    inner class SingerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName : TextView = itemView.findViewById(R.id.tvName)
    }
}