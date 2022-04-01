package com.example.studying

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainer, FishFragment(), null)
//            .commit()
        val listPositions = mutableListOf<PositionEntity>(
            PositionEntity(1, "Dir"),
            PositionEntity(2, "Buh"), PositionEntity(3, "Rab")
        )

        val db = Room.databaseBuilder(
            applicationContext,
            DataBase::class.java, "positions.db"
        ).build()

        db.positionDao().insertAllPositionRx(listPositions).subscribeOn(Schedulers.newThread())
            .subscribe()
         db.positionDao().getAllRx().subscribeOn(Schedulers.newThread())
            .subscribe { emmiter -> println(emmiter.toString()) }


    }

}