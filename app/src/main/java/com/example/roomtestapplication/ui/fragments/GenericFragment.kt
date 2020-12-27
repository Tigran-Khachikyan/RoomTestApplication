package com.example.roomtestapplication.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.FragmentHostBinding
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.repositories.Repository
import com.example.roomtestapplication.ui.MainActivity
import com.example.roomtestapplication.ui.adapter.RecyclerAdapter
import com.example.roomtestapplication.ui.adapter.RecyclerHolder
import com.example.roomtestapplication.ui.viewmodels.GenericViewModel
import com.example.roomtestapplication.ui.viewmodels.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


abstract class GenericFragment<T, K> : Fragment() {

    private lateinit var binding: FragmentHostBinding
    protected val adapterRec by lazy { RecyclerAdapter { createRecyclerHolder(it) } }
    private val viewModel by viewModels<GenericViewModel<T, K>> { ViewModelFactory(repository) }
    protected abstract val repository: Repository<T, K>
    protected lateinit var database: TaskDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)

        database = (context as MainActivity).database
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

        binding.recycler.apply {
            setHasFixedSize(true)
            this.adapter = adapterRec
        }
        binding.fabAdd.setOnClickListener { showBottomSheetDialog() }

        binding.swipeRefresh.setOnRefreshListener { viewModel.refresh() }

        viewModel.data.observe(viewLifecycleOwner, {
            adapterRec.updateData(it)
            if (binding.swipeRefresh.isRefreshing)
                binding.swipeRefresh.isRefreshing = false
        })
    }

    protected abstract fun showBottomSheetDialog(editableItem: K? = null)
    protected abstract fun showAdditionalInfo(item: K)
    protected abstract fun createRecyclerHolder(parent: ViewGroup): RecyclerHolder<K>

    protected fun remove(item: T) {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.remove(item)
        }
    }

    protected fun add(item: T) {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.add(item)
        }
    }

    protected fun update(item: T) {
        lifecycleScope.launch(Dispatchers.IO) {
            repository.update(item)
        }
    }

    protected fun showSnackBar(text: String) {
        val snackbar = Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG)
        val textView = snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.maxLines = 20
        snackbar.show()
    }

}

