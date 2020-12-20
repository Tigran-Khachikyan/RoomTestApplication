package com.example.roomtestapplication.fragments

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.roomtestapplication.models.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EmployeeFragment : GenericFragment<Employee>() {


    override fun initialize() {
        binding.etCompanyId.visibility = View.VISIBLE
        binding.etInput.hint = "Insert Employee"
        binding.etCompanyId.hint = "Insert company Id"
        binding.btnAdd.text = "add"
        binding.btnAdd.setOnClickListener { confirmChanges() }

        binding.recycler.apply {
            setHasFixedSize(true)
            this.adapter = adapterGeneric
        }
    }

    override fun edit(item: Employee) {
        binding.etInput.setText(item.fullName)
        binding.etCompanyId.setText(item.cId.toString())
        updatedId = item.id
        binding.btnAdd.text = "Update"
    }

    override fun confirmChanges() {
        val name = binding.etInput.text.toString()
        val cId = binding.etCompanyId.text.toString()
        if (name.trim().isEmpty()) {
            Toast.makeText(requireContext(), "No name", Toast.LENGTH_SHORT).show()
            return
        }
        if (cId.trim().isEmpty()) {
            Toast.makeText(requireContext(), "wrong company Id", Toast.LENGTH_SHORT).show()
            return
        }
        val companyId = try {
            cId.toInt()
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "wrong company Id", Toast.LENGTH_SHORT).show()
            return
        }
        if (updatedId != -1) update(Employee(name, companyId).apply { this.id = updatedId })
        else add(Employee(name, companyId))

        binding.etCompanyId.text.clear()
        binding.etInput.text.clear()
    }

    override fun observeData() {
        database.getEmployeeDao().getAll().observe(viewLifecycleOwner) {
            adapterGeneric.setData(it)
        }
    }

    override fun add(item: Employee) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getEmployeeDao().add(item)
        }
    }

    override fun update(item: Employee) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getEmployeeDao().update(item)
        }
    }

    override fun remove(item: Employee) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                database.getEmployeeDao().remove(item)
            } catch (ex: Exception) {
                Log.d("yayyyys55", "Employee removing exception: " + ex.message)
            }
        }
    }

}