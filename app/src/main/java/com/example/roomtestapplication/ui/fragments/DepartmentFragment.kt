package com.example.roomtestapplication.ui.fragments

import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.BottomSheetDepBinding
import com.example.roomtestapplication.databinding.HolderDepBinding
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.ui.adapter.RecyclerHolder
import com.example.roomtestapplication.ui.models.DepartmentDetails
import com.example.roomtestapplication.repositories.DepartmentRepository
import com.example.roomtestapplication.repositories.Repository
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder


class DepartmentFragment : GenericFragment<Department, Department>() {

    override val repository: Repository<Department, Department>
        get() = DepartmentRepository(database)

    override fun showBottomSheetDialog(editableItem: Department?) {

        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_dep, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(sheetView)
        val bsd = BottomSheetDepBinding.bind(sheetView)

        bsd.tvIntro.text = if (editableItem != null) "Edit department" else "Add department"
        editableItem?.run { bsd.etName.setText(name) }
        bsd.etName.addTextChangedListener {
            if (bsd.tilName.isErrorEnabled)
                bsd.tilName.isErrorEnabled = false
        }
        bsd.btnSave.setOnClickListener {
            val text = bsd.etName.text?.toString()
            if (text != null && text.isNotEmpty()) {
                if (editableItem != null) update(Department(text).apply { id = editableItem.id })
                else add(Department(text))
                dialog.dismiss()
            } else {
                bsd.tilName.isErrorEnabled = true
            }
        }
        dialog.show()
    }


    override fun showAdditionalInfo(item: Department) {
        lifecycleScope.launch(Dispatchers.IO) {
            val detail = repository.getDetails(item.id)
            if (detail != null && detail is DepartmentDetails)
                withContext(Dispatchers.Main) {
              /*      val builder = StringBuilder()
                    detail.positions?.forEach {
                        builder.append(it.title)
                        if (it.remote) builder.append(" - Remote ")
                        builder.append("\n")
                    }
                    showSnackBar(builder.toString())*/
                }
        }
    }


    override fun createRecyclerHolder(parent: ViewGroup): RecyclerHolder<Department> {
        return object : RecyclerHolder<Department>(parent, R.layout.holder_dep) {

            private val binding = HolderDepBinding.bind(this.itemView)
            override fun bind(model: Department) {

                binding.tvId.text = model.id.toString()
                binding.tvName.text = model.name

                binding.root.setOnClickListener {
                    val item = adapterRec.data?.get(layoutPosition)
                    item?.let { showAdditionalInfo(it) }
                }
                binding.root.setOnLongClickListener {
                    val item = adapterRec.data?.get(layoutPosition)
                    item?.let { showBottomSheetDialog(it) }
                    true
                }
                binding.icTrash.setOnClickListener {
                    val item = adapterRec.data?.get(layoutPosition)
                    item?.let { remove(it) }
                }
            }
        }
    }
}