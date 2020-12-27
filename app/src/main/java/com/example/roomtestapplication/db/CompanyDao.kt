package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Company
import com.example.roomtestapplication.ui.models.CompanyDetails

@Dao
interface CompanyDao {

    @Query("SELECT * FROM COMPANY")
    fun getObservable(): LiveData<List<Company>?>

    @Query("SELECT * FROM COMPANY")
    fun getAll(): List<Company>?

    @Transaction
    @Query("SELECT * FROM COMPANY WHERE COM_ID =:id")
    fun getDetails(id: Int): CompanyDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(company: Company)

    @Update
    suspend fun update(company: Company)

    @Query("DELETE FROM COMPANY WHERE COM_ID =:id")
    suspend fun remove(id: Int)

    @Delete
    suspend fun remove(company: Company)

    @Query("DELETE FROM COMPANY")
    suspend fun removeAll()
}