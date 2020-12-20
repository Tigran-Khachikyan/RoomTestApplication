package com.example.roomtestapplication.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.roomtestapplication.fragments.CompanyFragment
import com.example.roomtestapplication.fragments.EmployeeFragment


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val titles = arrayOf("Company", "Employee")

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CompanyFragment()
            else -> EmployeeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence = titles[position]

    override fun getCount() = 2
}