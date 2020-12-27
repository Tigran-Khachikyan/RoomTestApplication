package com.example.roomtestapplication.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter<K>(
    private val createHolder: (ViewGroup) -> RecyclerHolder<K>
) : RecyclerView.Adapter<RecyclerHolder<K>>() {

    var data: List<K>? = null

    fun updateData(data: List<K>?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder<K> {
        return createHolder.invoke(parent)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: RecyclerHolder<K>, position: Int) {
        data?.run { holder.bind(get(position)) }
    }
}