package com.example.roomtestapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.HolderGenericBinding
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.GenericType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GenericHolder<T : GenericType>(
    parent: ViewGroup,
    private val remove: (Int) -> Unit,
    private val edit: (Int) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.holder_generic, parent, false)
) {

    private val binding = HolderGenericBinding.bind(itemView)
    private val database by lazy { TaskDatabase.invoke(parent.context) }

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
            is Department -> {
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
                binding.tvDep.visibility = View.VISIBLE
                binding.tvId.text = entity.id.toString()
                CoroutineScope(Dispatchers.IO).launch {
                 /*   val compName = database.getEmployeeDao().getCompanyName(entity.cId)
                    val depName = database.getEmployeeDao().getDepartmentName(entity.dId)
                    withContext(Dispatchers.Main) {
                        val comDisplay = "Company N${entity.cId} - $compName"
                        val depDisplay = "Dep: N${entity.dId} - $depName"
                        binding.tvCompany.text = comDisplay
                        binding.tvDep.text = depDisplay
                    }*/
                    val compName = entity.cId?.let{database.getEmployeeDao().getCompanyName(it)}
                    val depName = entity.dId?.let{database.getEmployeeDao().getDepartmentName(it)}
                    withContext(Dispatchers.Main) {
                        val comDisplay = "Company N${entity.cId} - $compName"
                        val depDisplay = "Dep: N${entity.dId} - $depName"
                        binding.tvCompany.text = comDisplay
                        binding.tvDep.text = depDisplay
                    }
                }
                binding.icTrash.setOnClickListener { remove.invoke(layoutPosition) }
                binding.root.setOnClickListener { edit.invoke(layoutPosition) }
            }
        }
    }
}


