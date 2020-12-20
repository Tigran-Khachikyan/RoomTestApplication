package com.example.roomtestapplication.models

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "EMPLOYEE",
    foreignKeys = [
        ForeignKey(
            entity = Company::class,
            parentColumns = ["ID"],
            childColumns = ["COMPANY_ID"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.SET_DEFAULT,
            deferred = false
        )
    ]
)
data class Employee(

    @ColumnInfo(name = "FULL_NAME")
    val fullName: String,
    @ColumnInfo(name = "COMPANY_ID")
    val cId: Int = 23
) : GenericType {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    var id: Int = 0
}

// onDelete = ForeignKey.CASCADE,  +
// onDelete = ForeignKey.NO_ACTION, + rejecting  -  FOREIGN KEY constraint failed (code 787 SQLITE_CONSTRAINT_FOREIGNKEY[787])
// onDelete = ForeignKey.RESTRICT, + rejecting  - FOREIGN KEY constraint failed (code 1811 SQLITE_CONSTRAINT_TRIGGER[1811])
// onDelete = ForeignKey.SET_NULL, + worked with Int?
// onDelete = ForeignKey.SET_DEFAULT, - not worked without Int? (only set to null) - EMPLOYEE.COMPANY_ID (code 1299 SQLITE_CONSTRAINT_NOTNULL[1299])