package com.example.roomtestapplication.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Employee
import com.example.roomtestapplication.models.EmployeeDetails

class EmployeeRepository(val db: TaskDatabase) : Repository<Employee, EmployeeDetails> {

    override val database: TaskDatabase
        get() = db

    override fun getObservable(): LiveData<List<EmployeeDetails>?> {
        return db.getEmployeeDao().getObservable()
    }

    override suspend fun getAll(): List<EmployeeDetails>? {
        return db.getEmployeeDao().getAll()
    }

    override suspend fun getDetails(id: Int): Any? {
        return db.getEmployeeDao().getDetails(id)
    }

    override suspend fun add(item: Employee) {
        Log.d("kasdjh44", "add")

        db.getEmployeeDao().add(item)
    }

    override suspend fun update(item: Employee) {
        Log.d("kasdjh44", "update")
        db.getEmployeeDao().update(item)
    }

    override suspend fun remove(id: Int) {
        db.getEmployeeDao().remove(id)
    }

    override suspend fun remove(item: Employee) {
        db.getEmployeeDao().remove(item)
    }

    override suspend fun removeAll() {
        db.getEmployeeDao().removeAll()
    }
}