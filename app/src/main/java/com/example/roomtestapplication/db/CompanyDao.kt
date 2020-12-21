package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.models.CompanyWithEmployees

@Dao
interface CompanyDao {

    @Query("SELECT * FROM COMPANY")
    fun getAll(): LiveData<List<Company>?>

    @Transaction
    @Query("SELECT * FROM COMPANY")
    fun getCompaniesWithEmployees(): LiveData<List<CompanyWithEmployees>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(company: Company)

    @Update
    suspend fun update(company: Company)

    @Query("DELETE FROM COMPANY WHERE ID =:id")
    suspend fun remove(id: Int)

    @Delete
    suspend fun remove(company: Company)

    @Query("DELETE FROM COMPANY")
    suspend fun removeAll()
}