package com.example.roomtestapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.roomtestapplication.R
import com.example.roomtestapplication.adapter.GenericRecAdapter
import com.example.roomtestapplication.databinding.FragmentHostBinding
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.GenericType
import java.lang.StringBuilder

abstract class GenericFragment<T : GenericType> : Fragment() {

    protected lateinit var binding: FragmentHostBinding
    protected var updatedId: Int = -1 //index if not updating
    protected val database by lazy { TaskDatabase.invoke(requireContext()) }
    protected val adapterGeneric by lazy {
        GenericRecAdapter<T>(
            { remove(it) },
            { edit(it) },
            { showEmployees(it) }
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

        initialize()
        observeData()
        binding.swipeRefresh.setOnRefreshListener {
            adapterGeneric.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    protected fun isUpdating(): Boolean = updatedId != -1

    protected fun setAddingMode() {
        updatedId = -1
        binding.btnAdd.text = "Add"
    }

    private fun showEmployees(employees: List<Employee>?) {
        var text = StringBuilder().apply {
            employees?.forEach {
                append(it.id)
                append(" - ")
                append(it.fullName)
                append("\n")
            }
        }.toString()

        if (text.isEmpty())
            text = "No Employee found!"
        Toast.makeText(requireContext(), text, Toast.LENGTH_LONG).show()
    }

    protected abstract fun initialize()
    protected abstract fun edit(item: T)
    protected abstract fun confirmChanges()
    protected abstract fun observeData()
    protected abstract fun add(item: T)
    protected abstract fun update(item: T)
    protected abstract fun remove(item: T)

}

