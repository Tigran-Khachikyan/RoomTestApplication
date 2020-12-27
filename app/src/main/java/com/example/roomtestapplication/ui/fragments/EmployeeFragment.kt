package com.example.roomtestapplication.ui.fragments

import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.BottomSheetEmployeeBinding
import com.example.roomtestapplication.databinding.HolderEmployeeBinding
import com.example.roomtestapplication.models.*
import com.example.roomtestapplication.repositories.*

import com.example.roomtestapplication.ui.adapter.RecyclerHolder
import com.example.roomtestapplication.ui.initialize
import com.example.roomtestapplication.ui.models.EmployeeDetails
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class EmployeeFragment : GenericFragment<Employee, EmployeeDetails>() {

    override val repository: Repository<Employee, EmployeeDetails>
        get() = EmployeeRepository(database)

    override fun showBottomSheetDialog(editableItem: EmployeeDetails?) {
        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_employee, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(sheetView)
        val bsd = BottomSheetEmployeeBinding.bind(sheetView)

        bsd.tvIntro.text = if (editableItem != null) "Edit employee" else "Add employee"
        editableItem?.run {
            bsd.etName.setText(employee.name)
            bsd.etExperience.setText(employee.experience.toString())
            bsd.checkboxRemote.isChecked = employee.remote
            bsd.etSalary.setText(employee.salary.toString())
        }
        bsd.etName.addTextChangedListener {
            if (bsd.tilFullName.isErrorEnabled)
                bsd.tilFullName.isErrorEnabled = false
        }

        bsd.etExperience.addTextChangedListener {
            if (bsd.tilExperience.isErrorEnabled)
                bsd.tilExperience.isErrorEnabled = false
        }

        bsd.etSalary.addTextChangedListener {
            if (bsd.tilSalary.isErrorEnabled)
                bsd.tilSalary.isErrorEnabled = false
        }

        var positions: List<Position>? = null
        lifecycleScope.launch(Dispatchers.IO) {
            positions = PositionRepository(database).getAll()?.map { it.position }
            withContext(Dispatchers.Main) {
                val pos =
                    editableItem?.run { positions?.map { it.title }?.indexOf(position.title) } ?: 0
                bsd.spinnerPosition.initialize(
                    requireContext(),
                    positions?.map { it.id to it.title }?.toMap(),
                    pos
                ) {
                    val remoteIsSupported = positions?.get(it)?.remote
                    bsd.checkboxRemote.visibility =
                        if (remoteIsSupported != null && remoteIsSupported) View.VISIBLE
                        else View.GONE
                }
            }
        }

        var companies: List<Company>? = null
        lifecycleScope.launch(Dispatchers.IO) {
            companies = CompanyRepository(database).getAll()
            withContext(Dispatchers.Main) {
                val pos = editableItem?.run { companies?.map { it.name }?.indexOf(comName) } ?: 0
                bsd.spinnerCompany.initialize(
                    requireContext(),
                    companies?.map { it.id to it.name }?.toMap(),
                    pos
                )
            }
        }

        bsd.btnSave.setOnClickListener {

            val nameInput = bsd.etName.text?.toString()
            val expInput = bsd.etExperience.text?.toString()
            val salInput = bsd.etSalary.text?.toString()
            if (nameInput == null || nameInput.isEmpty()) {
                bsd.tilFullName.isErrorEnabled = true
                return@setOnClickListener
            }
            if (expInput == null || expInput.isEmpty()) {
                bsd.tilExperience.isErrorEnabled = true
                return@setOnClickListener
            }
            if (salInput == null || salInput.isEmpty()) {
                bsd.tilSalary.isErrorEnabled = true
                return@setOnClickListener
            }

            val experienceInput = try {
                expInput.toFloat()
            } catch (ex: Exception) {
                return@setOnClickListener
            }

            val salaryInput = try {
                salInput.toInt()
            } catch (ex: Exception) {
                return@setOnClickListener
            }

            val remote =
                bsd.checkboxRemote.visibility == View.VISIBLE && bsd.checkboxRemote.isChecked
            if (positions != null && companies != null) {

                editableItem?.run {
                    update(
                        Employee(
                            nameInput,
                            experienceInput,
                            salaryInput,
                            remote,
                            positions!![bsd.spinnerPosition.selectedItemPosition].id,
                            companies!![bsd.spinnerCompany.selectedItemPosition].id,
                        ).apply { id = employee.id }
                    )
                }
                editableItem ?: run {
                    add(
                        Employee(
                            nameInput,
                            experienceInput,
                            salaryInput,
                            remote,
                            positions!![bsd.spinnerPosition.selectedItemPosition].id,
                            companies!![bsd.spinnerCompany.selectedItemPosition].id,
                        )
                    )
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun showAdditionalInfo(item: EmployeeDetails) {
/*        lifecycleScope.launch(Dispatchers.IO) {
            val detail = repository.getDetails(item.id)
            if (detail != null && detail is DepartmentDetails)
                withContext(Dispatchers.Main) {
                    showSnackBar(detail.toString())
                }
        }*/
    }

    override fun createRecyclerHolder(parent: ViewGroup): RecyclerHolder<EmployeeDetails> {
        return object : RecyclerHolder<EmployeeDetails>(parent, R.layout.holder_employee) {

            private val binding = HolderEmployeeBinding.bind(this.itemView)
            override fun bind(model: EmployeeDetails) {

                binding.tvId.text = model.employee.id.toString()
                binding.tvFullName.text = model.employee.name
                val experience = "Experience - " + model.employee.experience + " years"
                binding.tvExperience.text = experience
                val posDep = model.position.title + " in " + model.depName + " department"
                binding.tvPositionInDep.text = posDep
                binding.tvRemote.visibility = if (model.employee.remote) View.VISIBLE else View.GONE
                val salary = "Salary - ${model.employee.salary} EUR"
                binding.tvSalary.text = salary
                binding.tvCompany.text = model.comName

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
                    item?.let { remove(it.employee) }
                }
            }
        }
    }
}