package com.example.roomtestapplication.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.roomtestapplication.R
import com.example.roomtestapplication.adapter.CompanyAdapter
import com.example.roomtestapplication.databinding.FragmentHostBinding
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Company
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class CompanyFragment : Fragment(), CRUDFunctionality<Company> {

    private lateinit var binding: FragmentHostBinding
    private var updatedId: Int = -1
    private val database by lazy {
        TaskDatabase.invoke(requireContext())
    }
    private val adapterCom by lazy {
        CompanyAdapter(
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

        binding.etCompanyId.visibility = View.GONE
        binding.etInput.hint = "Insert company"
        binding.btnAdd.text = "add"
        binding.btnAdd.setOnClickListener { confirm() }

        binding.recycler.apply {
            setHasFixedSize(true)
            this.adapter = adapterCom
        }
    }

    private fun confirm() {
        val name = binding.etInput.text.toString()
        if (name.trim().isEmpty()) {
            Toast.makeText(requireContext(), "No name", Toast.LENGTH_SHORT).show()
            return
        }
        if (updatedId != -1) update(Company(name).apply { id = updatedId })
        else add(Company(name))

        binding.etInput.text.clear()
    }

    private fun edit(company: Company) {
        binding.etInput.setText(company.name)
        binding.etCompanyId.setText(company.id.toString())
        updatedId = company.id
        binding.btnAdd.text = "Update"
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
        updatedId = -1
        binding.btnAdd.text = "Add"
    }

    override fun remove(item: Company) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                database.getCompanyDao().remove(item)
            }catch (ex: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(requireContext(),ex.message,Toast.LENGTH_LONG).show()
                    Log.d("yayyyys55","company removing exception: " + ex.message)
                }
            }
        }
    }

    override fun observeData() {
        database.getCompanyDao().getAll().observe(viewLifecycleOwner) {
            adapterCom.setCompanyList(it)
        }
    }
}