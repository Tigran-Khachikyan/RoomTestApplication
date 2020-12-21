package com.example.roomtestapplication.ui.fragments

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.roomtestapplication.R
import com.example.roomtestapplication.models.Department
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DepartmentFragment : GenericFragment<Department>() {

    override fun initialize() {
        binding.etCompanyId.visibility = View.GONE
        binding.etDepId.visibility = View.GONE
    }

    override fun edit(item: Department) {
        binding.etInput.setText(item.name)
        binding.etCompanyId.visibility = View.GONE
        updatedId = item.id
        binding.btnAdd.text = getString(R.string.update)
    }

    override fun confirmChanges() {
        val name = binding.etInput.text.toString()
        if (name.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Department name missing", Toast.LENGTH_SHORT).show()
            return
        }
        if (isUpdating()) update(Department(name).apply { id = updatedId })
        else add(Department(name))

        binding.etInput.text.clear()
    }

    override fun observeData() {
        database.getDepartment().getAll().observe(viewLifecycleOwner, {
            adapterGeneric.setData(it)
        })
    }

    override fun add(item: Department) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getDepartment().add(item)
        }
    }

    override fun update(item: Department) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getDepartment().update(item)
        }
        setAddingMode()
    }

    override fun remove(item: Department) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                database.getDepartment().remove(item)
            } catch (ex: Exception) {
                Log.d("yayyyys55", "Department removing exception: " + ex.message)
            }
        }
    }


}