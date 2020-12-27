package com.example.roomtestapplication.ui.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.Department

@Entity(
    primaryKeys = ["JUN_COM_ID", "JUN_DEP_ID"],
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            childColumns = arrayOf("JUN_COM_ID"),
            parentColumns = arrayOf("COM_ID")
        ), ForeignKey(
            entity = Department::class,
            childColumns = arrayOf("JUN_DEP_ID"),
            parentColumns = arrayOf("DEP_ID")
        )
    ]
)
class CompanyDepartmentJunction(

    @ColumnInfo(name = "JUN_COM_ID")
    val comId: Int,
    @ColumnInfo(name = "JUN_DEP_ID")
    val depId: Int
)