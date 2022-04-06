package com.example.studying

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "positions")
data class PositionEntity(@PrimaryKey
                    val id:Int,
                          val name:String)

