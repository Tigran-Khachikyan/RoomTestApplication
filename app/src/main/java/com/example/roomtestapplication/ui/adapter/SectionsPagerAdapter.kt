package com.example.roomtestapplication.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.roomtestapplication.ui.fragments.CompanyFragment
import com.example.roomtestapplication.ui.fragments.DepartmentFragment
import com.example.roomtestapplication.ui.fragments.EmployeeFragment


class SectionsPagerAdapter(fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles = arrayOf("Company", "Department", "Employee")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CompanyFragment()
            1 -> DepartmentFragment()
            else -> EmployeeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    override fun getCount() = 3
}