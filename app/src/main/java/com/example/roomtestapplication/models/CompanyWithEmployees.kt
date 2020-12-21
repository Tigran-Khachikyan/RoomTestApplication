package com.example.roomtestapplication.models

import androidx.room.Embedded
import androidx.room.Relation

data class CompanyWithEmployees(
    @Embedded
    val company: Company,
    @Relation(
        parentColumn = "ID",
        entityColumn = "ID",
        entity = Employee::class
    )
    val employees: List<Employee>
)