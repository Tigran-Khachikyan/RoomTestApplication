package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Company

@Dao
interface CompanyDao {

    @Query("SELECT * FROM COMPANY")
    fun getAll(): LiveData<List<Company>?>

    @Query("SELECT * FROM COMPANY WHERE ID =:id")
    fun getById(id: Int): LiveData<Company?>

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