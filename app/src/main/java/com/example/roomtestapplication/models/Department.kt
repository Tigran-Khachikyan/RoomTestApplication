package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DEPARTMENT")
data class Department(

    @ColumnInfo(name = "DEP_NAME")
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "DEP_ID")
    var id: Int = 0
}