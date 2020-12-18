package com.example.roomtestapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.HolderEmployeeBinding
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeAdapter(
    private val remove: (Employee) -> Unit,
    private val edit: (Employee) -> Unit
) :
    RecyclerView.Adapter<EmployeeAdapter.Holder>() {

    private var employees: List<Employee>? = null

    fun setEmployeeList(employees: List<Employee>?) {
        this.employees = employees
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = HolderEmployeeBinding.bind(itemView)
        fun bind(employee: Employee) {
            binding.tvEmployee.text = employee.fullName
            binding.tvId.text = employee.id.toString()
            CoroutineScope(Dispatchers.IO).launch {
                val name = TaskDatabase.invoke(itemView.context).getEmployeeDao().getCompanyName(employee.cId)
                withContext(Dispatchers.Main) {
                    val displayName = employee.cId.toString() + " - "+name
                    binding.tvCompany.text = displayName
                }
            }
            binding.icTrash.setOnClickListener { employees?.run { remove.invoke(get(layoutPosition)) } }
            binding.root.setOnClickListener { employees?.run { edit.invoke(get(layoutPosition)) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_employee, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = employees?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        employees?.let { holder.bind(it[position]) }
    }

}