package com.example.roomtestapplication.repositories

import androidx.lifecycle.LiveData
import com.example.roomtestapplication.db.TaskDatabase
import com.example.roomtestapplication.models.Company

class CompanyRepository(val db: TaskDatabase) : Repository<Company, Company> {

    override val database: TaskDatabase
        get() = db

    override fun getObservable(): LiveData<List<Company>?> {
        return db.getCompanyDao().getObservable()
    }

    override suspend fun getAll(): List<Company>? {
        return db.getCompanyDao().getAll()
    }

    override suspend fun getDetails(id: Int): Any? {
        return db.getCompanyDao().getDetails(id)
    }

    override suspend fun add(item: Company) {
        db.getCompanyDao().add(item)
    }

    override suspend fun update(item: Company) {
        db.getCompanyDao().update(item)
    }

    override suspend fun remove(id: Int) {
        db.getCompanyDao().remove(id)
    }

    override suspend fun remove(item: Company) {
        db.getCompanyDao().remove(item)
    }

    override suspend fun removeAll() {
        db.getCompanyDao().removeAll()
    }
}