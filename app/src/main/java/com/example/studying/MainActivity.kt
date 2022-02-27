package com.example.studying

import Task1.books
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import task_3.Task3

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        books()
        users()
        Task3().mainFun()
    }

}