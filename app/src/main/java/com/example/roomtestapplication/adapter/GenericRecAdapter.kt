package com.example.roomtestapplication.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.GenericType

class GenericRecAdapter<T : GenericType>(
    private val remove: (T) -> Unit,
    private val edit: (T) -> Unit,
    private val showEmployees: (List<Employee>?) -> Unit
) :
    RecyclerView.Adapter<GenericHolder<T>>() {

    private var data: List<T>? = null

    fun setData(data: List<T>?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericHolder<T> =
        GenericHolder(
            parent,
            { data?.run { remove.invoke(get(it)) } },
            { data?.run { edit.invoke(get(it)) } },
            { data?.run { showEmployees.invoke(it) } }
        )

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: GenericHolder<T>, position: Int) {
        data?.run { holder.bind(get(position)) }
    }

}