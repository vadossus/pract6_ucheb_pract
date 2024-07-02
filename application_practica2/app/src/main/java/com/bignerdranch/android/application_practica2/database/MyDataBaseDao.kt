package com.bignerdranch.android.application_practica2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MyDataBaseDao {

    @Insert
    fun insert(vararg data: MyData)

    @Update
    fun update(vararg data: MyData)

    @Query("SELECT * FROM mydata")
    fun getMyData(): Flow<List<MyData>>

}