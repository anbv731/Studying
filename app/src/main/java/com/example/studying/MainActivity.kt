package com.example.studying

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.annotation.MainThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.security.AccessController.getContext
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var image: ImageView
    lateinit var list: List<Something>

    companion object {
        const val IS_FETCHED = "IS_FETCHED"
    }

    private var isFetched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            isFetched = savedInstanceState.getBoolean(IS_FETCHED)
        }
        image = findViewById<ImageView>(R.id.imageviewId)
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        image.setImageDrawable(circularProgressDrawable)
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        usingThread()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_FETCHED, isFetched)
    }

    fun usingThread() {
        if (!isFetched) {
            Thread {
                Thread.sleep(5000)
                runOnUiThread {
                    image.visibility = View.GONE
                }
                isFetched = true
                list = parseJson(getJson(applicationContext, "irlix.json"))
                runOnUiThread {
                    recycler.adapter = RecyclerAdapter(list)
                }
            }.start()
        }
    }

    fun parseJson(json: String?): List<Something> {

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