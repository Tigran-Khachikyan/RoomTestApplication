package com.example.roomtestapplication.ui.models

import androidx.room.Embedded
import androidx.room.Relation
import com.example.roomtestapplication.models.Department
import com.example.roomtestapplication.models.Position

data class DepartmentDetails(

    @Embedded
    val department: Department,

    //One-to-many relations
    // Android developer
    // HR Manager
 /*   @Relation(
        parentColumn = "DEP_ID",
        entityColumn = "POS_DEP_ID",
        entity = Position::class
    )
    val positions: List<Position>?*/
)