package com.example.studying

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fun getJson(context: Context, fileName: String):String?{
            val jsonString: String
            try{
                jsonString=context.assets.open(fileName).bufferedReader().use { it.readText() }

            }catch (ioException: IOException) {
                ioException.printStackTrace()
                return null
            }
            return jsonString

        }
        val json=getJson(this,"irlix.json")
        val gson= Gson()

        val listSomthingType = object : TypeToken<List<Something>>() {}.type
        var somthings: List<Something> = gson.fromJson(json, listSomthingType)
        somthings.forEachIndexed { idx, something -> println(something.id ) }
    }

}