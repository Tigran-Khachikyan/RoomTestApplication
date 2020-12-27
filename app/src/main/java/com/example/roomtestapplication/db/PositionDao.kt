package com.example.roomtestapplication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomtestapplication.models.Position
import com.example.roomtestapplication.ui.models.PositionDetails

@Dao
interface PositionDao {


    @Transaction
    @Query("SELECT  POSITION.*, DEPARTMENT.DEP_NAME FROM POSITION INNER JOIN DEPARTMENT ON POS_DEP_ID = DEP_ID")
    fun getObservable(): LiveData<List<PositionDetails>?>

    @Transaction
    @Query("SELECT  POSITION.*, DEPARTMENT.DEP_NAME FROM POSITION INNER JOIN DEPARTMENT ON POS_DEP_ID = DEP_ID")
    fun getAll(): List<PositionDetails>?

    @Transaction
    @Query("SELECT POSITION.*, DEPARTMENT.DEP_NAME FROM POSITION INNER JOIN DEPARTMENT ON POS_DEP_ID = DEP_ID WHERE POS_ID =:id")
    fun getDetails(id: Int): PositionDetails?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(pos: Position)

    @Update
    suspend fun update(pos: Position)

    @Query("DELETE FROM POSITION WHERE POS_ID =:id")
    suspend fun remove(id: Int)

    @Delete
    suspend fun remove(pos: Position)

    @Query("DELETE FROM POSITION")
    suspend fun removeAll()
}