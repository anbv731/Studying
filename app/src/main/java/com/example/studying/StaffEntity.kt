package com.example.studying

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "staff")
data class StaffEntity(
    @PrimaryKey
    val id:Int,
    val name:String,
    val age: String,
    val experience:String
)