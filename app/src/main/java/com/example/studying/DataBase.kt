package com.example.studying

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PositionEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class DataBase:RoomDatabase() {
    abstract  fun positionDao():PositionDao
}