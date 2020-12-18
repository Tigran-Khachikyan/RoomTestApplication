package com.example.roomtestapplication.fragments

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


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