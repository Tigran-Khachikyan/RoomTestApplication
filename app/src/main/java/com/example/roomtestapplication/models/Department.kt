package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DEPARTMENT")
data class Department(
    @ColumnInfo(name = "NAME")
    val name: String
) : GenericType {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0
}