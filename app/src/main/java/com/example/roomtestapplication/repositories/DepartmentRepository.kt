package com.example.roomtestapplication.repositories

import androidx.lifecycle.LiveData
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Department

class DepartmentRepository(val db: TaskDatabase) : Repository<Department, Department> {

    override val database: TaskDatabase
        get() = db

    override fun getObservable(): LiveData<List<Department>?> {
        return db.getDepartmentDao().getObservable()
    }

    override suspend fun getAll(): List<Department>? {
        return db.getDepartmentDao().getAll()
    }

    override suspend fun getDetails(id: Int): Any? {
        return db.getDepartmentDao().getDetails(id)
    }

    override suspend fun add(item: Department) {
        db.getDepartmentDao().add(item)
    }

    override suspend fun update(item: Department) {
        db.getDepartmentDao().update(item)
    }

    override suspend fun remove(id: Int) {
        db.getDepartmentDao().remove(id)
    }

    override suspend fun remove(item: Department) {
        db.getDepartmentDao().remove(item)
    }

    override suspend fun removeAll() {
        db.getDepartmentDao().removeAll()
    }

}