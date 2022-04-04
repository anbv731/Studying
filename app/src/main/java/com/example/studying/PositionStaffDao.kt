package com.example.studying

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PositionStaffDao {
    @Query("SELECT * FROM positions")
    fun getAllPos(): List<PositionEntity>

    @Query("SELECT * FROM positions")
    fun getAllPosRx(): Single<List<PositionEntity>>

    @Query("SELECT * FROM positions WHERE name LIKE :name")
    fun findByNamePos(name: String): PositionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosition( list: List<PositionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPositionRx( list: List<PositionEntity>):Completable

    @Delete
    fun deletePos(todo: PositionEntity)

    @Update
    fun updatePos(vararg todos: PositionEntity)

    @Query("SELECT * FROM staff")
    fun getAllStaff(): List<StaffEntity>

    @Query("SELECT * FROM staff")
    fun getAllStaffRx(): Single<List<StaffEntity>>

    @Query("SELECT * FROM staff WHERE name LIKE :name")
    fun findByNameStaff(name: String): StaffEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStaff( list: List<StaffEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllStaffRx( list: List<StaffEntity>):Completable

    @Delete
    fun deleteStaff(todo: StaffEntity)

    @Update
    fun updateStaff(vararg todos: StaffEntity)
}