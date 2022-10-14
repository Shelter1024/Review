package com.example.testrecyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author: Shelter
 * Create time: 2022/10/11, 11:23.
 */
class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyHolder>() {
    private var selectedPosition: Int = 0

    private val itemClickListener: (Int) -> Unit = {
        selectedPosition = it
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.itemView.setOnClickListener {
            Log.d("Shelter", "MyAdapter onBindViewHolder position:${holder.adapterPosition}")
            itemClickListener.invoke(holder.adapterPosition)
        }
        if (position == selectedPosition) {
            holder.itemView.scaleX = 1.1f
            holder.itemView.scaleY = 1.1f
            holder.itemView.setBackgroundResource(R.drawable.shape_item_bg_selected)
        } else {
            holder.itemView.scaleX = 1.0f
            holder.itemView.scaleY = 1.0f
            holder.itemView.setBackgroundResource(R.drawable.shape_item_bg)
        }

    }

    override fun getItemCount(): Int = 20

    inner class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
    }


}