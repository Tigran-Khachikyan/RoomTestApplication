package com.example.roomtestapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtestapplication.R
import com.example.roomtestapplication.adapter.CompanyAdapter
import com.example.roomtestapplication.adapter.EmployeeAdapter
import com.example.roomtestapplication.databinding.FragmentHostBinding
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Employee
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class CompanyFragment() : Fragment() {

    private lateinit var binding: FragmentHostBinding
    private val database by lazy {
        TaskDatabase.invoke(requireContext())
    }
    private val adapterCom by lazy {
        CompanyAdapter {
            CoroutineScope(Dispatchers.IO).launch {
                database.getCompanyDao().remove(it)
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

        binding.etCompanyId.visibility = View.GONE
        binding.etInput.hint = "Insert company"
        binding.btnAdd.setOnClickListener { add() }

        binding.recycler.apply {
            setHasFixedSize(true)
            this.adapter = adapterCom
        }

    }

    private fun add() {
        val name = binding.etInput.text.toString()
        if (name.trim().isEmpty()) {
            Toast.makeText(requireContext(), "No name", Toast.LENGTH_SHORT).show()
            return
        }
        CoroutineScope(Dispatchers.IO).launch {
            database.getCompanyDao().add(Company(name))
        }
    }

    private fun observe() {
        database.getCompanyDao().getAll().observe(viewLifecycleOwner, {
            adapterCom.setCompanyList(it)
        })
    }


}