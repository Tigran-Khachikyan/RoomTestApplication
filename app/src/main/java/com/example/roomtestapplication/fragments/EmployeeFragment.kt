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
        binding.etDepId.visibility = View.VISIBLE
        binding.etInput.hint = "Insert Employee"
        binding.etCompanyId.hint = "Insert company Id"
        binding.etDepId.hint = "Insert department Id"
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
        binding.etDepId.setText(item.dId.toString())
        updatedId = item.id
        binding.btnAdd.text = "Update"
    }

    override fun confirmChanges() {
        val name = binding.etInput.text.toString()
        val cId = binding.etCompanyId.text.toString()
        val dId = binding.etDepId.text.toString()
        if (name.trim().isEmpty()) {
            Toast.makeText(requireContext(), "No name", Toast.LENGTH_SHORT).show()
            return
        }
        if (cId.trim().isEmpty()) {
            Toast.makeText(requireContext(), "wrong company Id", Toast.LENGTH_SHORT).show()
            return
        }
        if (dId.trim().isEmpty()) {
            Toast.makeText(requireContext(), "wrong dep Id", Toast.LENGTH_SHORT).show()
            return
        }
        val companyId = try {
            cId.toInt()
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "wrong company Id", Toast.LENGTH_SHORT).show()
            return
        }
        val depId = try {
            dId.toInt()
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "wrong dep Id", Toast.LENGTH_SHORT).show()
            return
        }
        if (isUpdating()) update(Employee(name, companyId, depId).apply { this.id = updatedId })
        else add(Employee(name, companyId, depId))

        binding.etCompanyId.text.clear()
        binding.etDepId.text.clear()
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
        setAddingMode()
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