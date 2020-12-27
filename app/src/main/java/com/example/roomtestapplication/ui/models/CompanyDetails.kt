package com.example.roomtestapplication.ui.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.Position

data class CompanyDetails(

    @Embedded
    val company: Company,

       @Relation(
           parentColumn = "COM_ID",
           entityColumn = "EMP_COM_ID"
       )
       val employees: List<Employee>?,

    @Relation(
        parentColumn = "COM_ID",
        entityColumn = "DEP_ID",
        associateBy = Junction(CompanyDepartmentJunction::class, parentColumn = "JUN_COM_ID", entityColumn = "JUN_DEP_ID"),
    )
    val departments: List<Department>?
)