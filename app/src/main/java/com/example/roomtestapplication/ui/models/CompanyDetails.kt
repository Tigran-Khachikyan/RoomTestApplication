package com.example.roomtestapplication.ui.models

import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.Position

data class CompanyDetails(
    val company: Company,
    val departments: List<Department>?,
    val positions: List<Position>?,
    val employees: List<Employee>?
)