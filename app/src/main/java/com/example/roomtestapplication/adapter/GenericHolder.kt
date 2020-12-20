package com.example.roomtestapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.HolderGenericBinding
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.GenericType

class GenericHolder<T : GenericType>(
    parent: ViewGroup,
    private val remove: (Int) -> Unit,
    private val edit: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.holder_generic, parent, false)
) {

    private val binding = HolderGenericBinding.bind(itemView)

    fun bind(entity: T) {

        when (entity) {
            is Company -> {
                binding.tvTitle.text = entity.name
                binding.tvCompany.visibility = View.GONE
                binding.tvDep.visibility = View.GONE
                binding.tvId.text = entity.id.toString()
                binding.icTrash.setOnClickListener { remove.invoke(layoutPosition) }
                binding.root.setOnClickListener { edit.invoke(layoutPosition) }
            }
            is Employee -> {
                binding.tvTitle.text = entity.fullName
                binding.tvCompany.visibility = View.VISIBLE
                binding.tvCompany.text = entity.cId.toString()
                binding.tvDep.visibility = View.VISIBLE
                binding.tvId.text = entity.id.toString()
                binding.icTrash.setOnClickListener { remove.invoke(layoutPosition) }
                binding.root.setOnClickListener { edit.invoke(layoutPosition) }
            }
        }
    }
}


