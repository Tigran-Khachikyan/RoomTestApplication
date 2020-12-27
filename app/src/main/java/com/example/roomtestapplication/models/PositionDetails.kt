package com.example.roomtestapplication.models

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Relation
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.Position

data class PositionDetails(

    @Embedded
    val position: Position,

    @ColumnInfo(name = "DEP_NAME")
    val depName: String,

    @Relation(
        parentColumn = "POS_ID",
        entityColumn = "EMP_POS_ID",
        entity = Employee::class
    )
    var employees: List<Employee>? = null
)
