package com.bignerdranch.android.application_practica2.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mydata")
data class MyData(
    @PrimaryKey(autoGenerate = true)
    val PrimaryKey: Int,
    val image: ByteArray,
    val name: String,
    val surname: String,
    val group: String,
)

