package com.example.studying

import androidx.room.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
interface PositionDao {
    @Query("SELECT * FROM positions")
    fun getAll(): List<PositionEntity>

    @Query("SELECT * FROM positions")
    fun getAllRx(): Single<List<PositionEntity>>

    @Query("SELECT * FROM positions WHERE name LIKE :name")
    fun findByName(name: String): PositionEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPosition( list: List<PositionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllPositionRx( list: List<PositionEntity>):Completable

    @Delete
    fun delete(todo: PositionEntity)

    @Update
    fun updateTodo(vararg todos: PositionEntity)
}