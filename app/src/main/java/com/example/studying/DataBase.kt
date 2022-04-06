package com.example.studying

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [PositionEntity::class,StaffEntity::class], version = 1, exportSchema = false)
abstract class DataBase:RoomDatabase() {
    abstract  fun positionStaffDao():PositionStaffDao
}