package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.*
import androidx.room.PrimaryKey

@Entity(tableName = "EMPLOYEE")
data class Employee(

    @ColumnInfo(name = "FULL_NAME")
    val fullName: String,
    @ColumnInfo(name = "COMPANY_ID")
    val cId: Int,
    @ColumnInfo(name = "DEP_ID")
    val dId: Int
) : GenericType {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0
}


