package com.example.roomtestapplication.ui.fragments

import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.BottomSheetPositionBinding
import com.example.roomtestapplication.databinding.HolderPosBinding
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.Position
import com.example.roomtestapplication.repositories.DepartmentRepository
import com.example.roomtestapplication.repositories.PositionRepository
import com.example.roomtestapplication.ui.adapter.RecyclerHolder
import com.example.roomtestapplication.models.PositionDetails
import com.example.roomtestapplication.repositories.Repository
import com.example.roomtestapplication.ui.SpinnerHolder
import com.example.roomtestapplication.ui.initialize
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PositionFragment : GenericFragment<Position, PositionDetails>() {

    override val repository: Repository<Position, PositionDetails>
        get() = PositionRepository(database)

    override fun showBottomSheetDialog(editableItem: PositionDetails?) {


        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_position, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(sheetView)
        val bsd = BottomSheetPositionBinding.bind(sheetView)

        bsd.tvIntro.text = if (editableItem != null) "Edit position" else "Add position"
        editableItem?.run {
            bsd.etTitle.setText(position.title)
            bsd.checkboxRemoteSupported.isChecked = position.remote
        }
        bsd.etTitle.addTextChangedListener {
            if (bsd.tilTitle.isErrorEnabled)
                bsd.tilTitle.isErrorEnabled = false
        }

        var departments: List<Department>? = null
        lifecycleScope.launch(Dispatchers.IO) {
            departments = DepartmentRepository(database).getAll()
            withContext(Dispatchers.Main) {
                val pos = editableItem?.run { departments?.map { it.name }?.indexOf(depName) } ?: 0
                bsd.spinnerDepartment.initialize(
                    requireContext(),
                    departments?.map { SpinnerHolder(it.id, it.name) },
                    pos
                )
            }
        }




        bsd.btnSave.setOnClickListener {
            val text = bsd.etTitle.text?.toString()
            if (text != null && text.isNotEmpty()) {

                if (departments != null) {

                    val isRemote = bsd.checkboxRemoteSupported.isChecked
                    editableItem?.run {
                        update(
                            Position(
                                text,
                                isRemote,
                                departments!![bsd.spinnerDepartment.selectedItemPosition].id
                            ).apply { id = position.id })
                    }
                    editableItem ?: run {
                        add(
                            (Position(
                                text,
                                isRemote,
                                departments!![bsd.spinnerDepartment.selectedItemPosition].id
                            ))
                        )
                    }
                    dialog.dismiss()
                }

            } else {
                bsd.tilTitle.isErrorEnabled = true
            }
        }
        dialog.show()
    }

    override fun showAdditionalInfo(item: PositionDetails) {
        lifecycleScope.launch(Dispatchers.IO) {
            val detail = repository.getDetails(item.position.id)
            if (detail != null && detail is PositionDetails)
                withContext(Dispatchers.Main) {
                    showSnackBar(detail.toString())
                }
        }
    }

    override fun createRecyclerHolder(parent: ViewGroup): RecyclerHolder<PositionDetails> {
        return object : RecyclerHolder<PositionDetails>(parent, R.layout.holder_pos) {

            private val binding = HolderPosBinding.bind(this.itemView)
            override fun bind(model: PositionDetails) {

                binding.tvId.text = model.position.id.toString()
                binding.tvTitle.text = model.position.title
                binding.tvRemote.visibility =
                    if (!model.position.remote) View.GONE else View.VISIBLE

                binding.tvDepartment.text = model.depName

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
                    item?.run { remove(position) }
                }
            }
        }
    }
}