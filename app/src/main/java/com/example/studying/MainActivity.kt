package com.example.studying

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var image: ImageView
    lateinit var list: List<Something>
    lateinit var savedJson: String

    companion object {
        const val IS_FETCHED = "IS_FETCHED"
        const val JSON = "JSON"
    }

    private var isFetched: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            isFetched = savedInstanceState.getBoolean(IS_FETCHED)
            savedJson = savedInstanceState.getString(JSON)!!
        }
        image = findViewById(R.id.imageviewId)
        val circularProgressDrawable = CircularProgressDrawable(this)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()
        image.setImageDrawable(circularProgressDrawable)
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        //usingThread()
        usingExecutor()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(IS_FETCHED, isFetched)
        if (isFetched) {
            outState.putString(JSON, savedJson)
        }
    }

    fun usingThread() {
        if (!isFetched) {
            Thread {
                Thread.sleep(5000)
                runOnUiThread {
                    image.visibility = View.GONE
                }
                savedJson = getJson(applicationContext, "irlix.json")!!
                isFetched = true
                list = parseJson(savedJson)

                runOnUiThread {
                    recycler.adapter = RecyclerAdapter(list)
                }
            }.start()
        } else {
            list = parseJson(savedJson)
            image.visibility = View.GONE
            recycler.adapter = RecyclerAdapter(list)
        }
    }

    fun usingExecutor() {
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {run{
            if (!isFetched) {
                    Thread.sleep(5000)
                    runOnUiThread {
                        image.visibility = View.GONE
                    }
                    savedJson = getJson(applicationContext, "irlix.json")!!
                    isFetched = true
                    list = parseJson(savedJson)

                    runOnUiThread {
                        recycler.adapter = RecyclerAdapter(list)
                    }
            }else {
                list = parseJson(savedJson)
                image.visibility = View.GONE
                recycler.adapter = RecyclerAdapter(list)
            }
        }
        }
    }
fun usingIntentService(){
    val myintentService= Intent(this,MyIntentService::class.java)
    startService(myintentService.putExtra("task","hsdsag"))
}
class MyIntentService :IntentService("MyName"){
    override fun onHandleIntent(p0: Intent?) {
        val label: String = p0?.getStringExtra("task")!!
        Thread.sleep(5000)

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