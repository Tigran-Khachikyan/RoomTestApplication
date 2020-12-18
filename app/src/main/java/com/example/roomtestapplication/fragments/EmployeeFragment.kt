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

class EmployeeFragment : Fragment() {

    private lateinit var binding: FragmentHostBinding
    private val database by lazy {
        TaskDatabase.invoke(requireContext())
    }
    private val adapterEmp by lazy {
        EmployeeAdapter {
            CoroutineScope(Dispatchers.IO).launch {
                database.getEmployeeDao().remove(it)
            }
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_host, container, false)
        binding = FragmentHostBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        observe()
    }


    private fun initViews() {

        binding.etCompanyId.visibility = View.VISIBLE


        binding.etInput.hint = "Insert employee"


        binding.btnAdd.setOnClickListener { add() }

        binding.recycler.apply {
            setHasFixedSize(true)
            this.adapter = adapterEmp
        }

    }

    private fun add() {

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
        val id = try {
            cId.toInt()
        } catch (ex: Exception) {
            Toast.makeText(requireContext(), "wrong company Id", Toast.LENGTH_SHORT).show()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            database.getEmployeeDao().add(Employee(name, id))
        }
    }

    private fun observe() {
        database.getEmployeeDao().getAll().observe(viewLifecycleOwner, {
            adapterEmp.setEmployeeList(it)
        })
    }

}