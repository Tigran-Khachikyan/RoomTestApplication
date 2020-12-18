package com.example.roomtestapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtestapplication.R
import com.example.roomtestapplication.databinding.HolderCompanyBinding
import com.example.roomtestapplication.models.Company

class CompanyAdapter(
    private val remove: (Company) -> Unit
) :
    RecyclerView.Adapter<CompanyAdapter.Holder>() {

    private var companies: List<Company>? = null

    fun setCompanyList(companies: List<Company>?) {
        this.companies = companies
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = HolderCompanyBinding.bind(itemView)
        fun bind(company: Company) {
            binding.tvCompany.text = company.name
            binding.tvId.text = company.id.toString()
            binding.icTrash.setOnClickListener { companies?.run { remove.invoke(get(layoutPosition)) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.holder_company, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int = companies?.size ?: 0

    override fun onBindViewHolder(holder: Holder, position: Int) {
        companies?.let { holder.bind(it[position]) }
    }

}