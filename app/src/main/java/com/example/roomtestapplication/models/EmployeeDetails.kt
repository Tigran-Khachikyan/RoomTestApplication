package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Embedded

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