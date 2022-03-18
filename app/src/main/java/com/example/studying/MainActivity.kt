package com.example.studying

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycler = findViewById(R.id.recycler)
        recycler.adapter = RecyclerAdapter(parseJson(getJson(this,"irlix.json")))
        recycler.layoutManager = LinearLayoutManager(this)


    }

    fun parseJson(json:String?):List<Something> {

        val gson = Gson()
        val listSomthingType = object : TypeToken<List<Something>>() {}.type
        return gson.fromJson(json, listSomthingType)


    }
    fun getJson(context: Context, fileName: String): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString

    }
    class secondThread:Thread(){
        override fun run() {

        }
        fun parseJson(json:String?):List<Something> {

            val gson = Gson()
            val listSomthingType = object : TypeToken<List<Something>>() {}.type
            return gson.fromJson(json, listSomthingType)
        }
        fun getJson(context: Context, fileName: String): String? {
            val jsonString: String
            try {
                jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString

        }
    }
}