package com.example.roomtestapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.roomtestapplication.R
import com.example.roomtestapplication.adapter.EmployeeAdapter
import com.example.roomtestapplication.databinding.FragmentHostBinding
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class EmployeeFragment : Fragment(), CRUDFunctionality<Employee> {

    private lateinit var binding: FragmentHostBinding
    private var updatedId: Int = -1
    private val database by lazy {
        TaskDatabase.invoke(requireContext())
    }
    private val adapterEmp by lazy {
        EmployeeAdapter(
            { remove(it) },
            { edit(it) }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_host, container, false)
        binding = FragmentHostBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observeData()
    }

    private fun initViews() {

        binding.etCompanyId.visibility = View.VISIBLE
        binding.etInput.hint = "Insert Employee"
        binding.etCompanyId.hint = "Insert company Id"
        binding.btnAdd.text = "add"
        binding.btnAdd.setOnClickListener { confirm() }

        binding.recycler.apply {
            setHasFixedSize(true)
            this.adapter = adapterEmp
        }
    }

    private fun confirm() {
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

    private fun edit(employee: Employee) {
        binding.etInput.setText(employee.fullName)
        binding.etCompanyId.setText(employee.cId.toString())
        updatedId = employee.id
        binding.btnAdd.text = "Update"
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
        updatedId = -1
        binding.btnAdd.text = "Add"
    }

    override fun remove(item: Employee) {
        CoroutineScope(Dispatchers.IO).launch {
            database.getEmployeeDao().remove(item)
        }
    }

    override fun observeData() {
        database.getEmployeeDao().getAll().observe(viewLifecycleOwner) {
            adapterEmp.setEmployeeList(it)
        }
    }

}