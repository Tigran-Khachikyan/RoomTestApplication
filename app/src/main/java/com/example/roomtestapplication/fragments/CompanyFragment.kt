package com.example.roomtestapplication.fragments

import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.roomtestapplication.models.Company
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyFragment : GenericFragment<Company>() {

    override fun initialize() {
        binding.etCompanyId.visibility = View.GONE
        binding.etInput.hint = "Insert company"
        binding.btnAdd.text = "add"
        binding.btnAdd.setOnClickListener { confirmChanges() }

        binding.recycler.apply {
            setHasFixedSize(true)
            this.adapter = adapterGeneric
        }
    }

    override fun edit(item: Company) {
        binding.etInput.setText(item.name)
        binding.etCompanyId.visibility = View.GONE
        updatedId = item.id
        binding.btnAdd.text = "Update"
    }

    override fun confirmChanges() {
        val name = binding.etInput.text.toString()
        if (name.trim().isEmpty()) {
            Toast.makeText(requireContext(), "Company name missing", Toast.LENGTH_SHORT).show()
            return
        }
        if (updatedId != -1) update(Company(name).apply { id = updatedId })
        else add(Company(name))

        binding.etInput.text.clear()
    }

    override fun observeData() {
        database.getCompanyDao().getAll().observe(viewLifecycleOwner, {
            adapterGeneric.setData(it)
        })
    }

    override fun add(item: Company) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getCompanyDao().add(item)
        }
    }

    override fun update(item: Company) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getCompanyDao().update(item)
        }
    }

    override fun remove(item: Company) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                database.getCompanyDao().remove(item)
            } catch (ex: Exception) {
                Log.d("yayyyys55", "company removing exception: " + ex.message)
            }
        }
    }


}