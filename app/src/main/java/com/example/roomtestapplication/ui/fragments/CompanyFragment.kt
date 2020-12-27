package com.example.roomtestapplication.ui.fragments

import android.app.DatePickerDialog
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.BottomSheetCompanyBinding
import com.example.roomtestapplication.databinding.HolderCompanyBinding
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.typeconverters.DateConverter
import com.example.roomtestapplication.repositories.CompanyRepository
import com.example.roomtestapplication.repositories.Repository
import com.example.roomtestapplication.ui.adapter.RecyclerHolder
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class CompanyFragment : GenericFragment<Company, Company>() {

    override val repository: Repository<Company, Company>
        get() =  CompanyRepository(database)

    override fun showBottomSheetDialog(editableItem: Company?) {

        val sheetView = layoutInflater.inflate(R.layout.bottom_sheet_company, null)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(sheetView)
        val bsd = BottomSheetCompanyBinding.bind(sheetView)

        var date: Date? = null
        bsd.tvIntro.text = if (editableItem != null) "Edit company" else "Add company"
        editableItem?.run {
            bsd.etName.setText(name)
            date = dateFounded
            val textDate = getString(R.string.founded) + " " + DateConverter().fromDate(date)
            bsd.tvCalendar.text = textDate
        }
        bsd.etName.addTextChangedListener {
            if (bsd.tilName.isErrorEnabled)
                bsd.tilName.isErrorEnabled = false
        }

        bsd.tvCalendar.setOnClickListener {
            val cal = Calendar.getInstance()
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireContext(), { _, year, monthOfYear, dayOfMonth ->

                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                date = cal.time
                val textDate = getString(R.string.founded) + " " + DateConverter().fromDate(date)
                bsd.tvCalendar.text = textDate
            }, y, m, d)

            dpd.show()
        }

        bsd.btnSave.setOnClickListener {
            val text = bsd.etName.text?.toString()
            if (text == null || text.isEmpty()) {
                bsd.tilName.isErrorEnabled = true
                return@setOnClickListener
            }

            if (date == null) {
                Toast.makeText(
                    requireContext(), "The date of foundation is not selected!", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (editableItem != null) update(Company(text, date!!).apply { id = editableItem.id })
            else add(Company(text, date!!))
            dialog.dismiss()
        }
        dialog.show()
    }


    override fun showAdditionalInfo(item: Company) {
        /* val companies : List<String>
         val companies : List<String>
         val companies : List<String>*/
    }

    override fun createRecyclerHolder(parent: ViewGroup): RecyclerHolder<Company> {
        return object : RecyclerHolder<Company>(parent, R.layout.holder_company) {

            private val binding = HolderCompanyBinding.bind(this.itemView)
            override fun bind(model: Company) {

                binding.tvId.text = model.id.toString()
                binding.tvName.text = model.name
                val founded =
                    getString(R.string.founded) + " " + DateConverter().fromDate(model.dateFounded)
                binding.tvFoundedDate.text = founded

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