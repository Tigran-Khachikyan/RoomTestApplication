package com.example.roomtestapplication.ui.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.Position

data class EmployeeDetails(

    @Embedded
    val employee: Employee,

    @Embedded
    val position: Position,

    @ColumnInfo(name = "DEP_NAME")
    val depName: String,

    @ColumnInfo(name = "COM_NAME")
    val comName: String
)