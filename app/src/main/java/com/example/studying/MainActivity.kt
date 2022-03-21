package com.example.studying

import android.app.IntentService
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {
    lateinit var savedJson: String
    lateinit var myBroadcastReceiver: MyBroadcastReceiver
    lateinit var recycler: RecyclerView
    lateinit var image: ProgressBar
    lateinit var list: List<Something>

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
        image = findViewById(R.id.progressBar)
        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        usingThread()
        // usingExecutor()
        //usingIntentService()

        myBroadcastReceiver = MyBroadcastReceiver()
        val intentFilter = IntentFilter(
            MyIntentService().ACTION_MYINTENTSERVICE
        )
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT)
        registerReceiver(myBroadcastReceiver, intentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myBroadcastReceiver)
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

        executor.execute {
            run {
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
                } else {
                    list = parseJson(savedJson)
                    image.visibility = View.GONE
                    recycler.adapter = RecyclerAdapter(list)
                }
            }
        }
    }

    fun usingIntentService() {
        if (!isFetched) {
            val myintentService = Intent(this, MyIntentService::class.java)
            startService(myintentService)
        } else {
            image.visibility = View.GONE
            recycler.adapter = RecyclerAdapter(parseJson(savedJson))
        }

    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            savedJson = intent
                .getStringExtra(MyIntentService().EXTRA_KEY_OUT)!!
            isFetched = true
            image.visibility = View.GONE
            recycler.adapter = RecyclerAdapter(parseJson(savedJson))
        }
    }

    class MyIntentService : IntentService("MyName") {
        val ACTION_MYINTENTSERVICE = "actionintentservice"
        val EXTRA_KEY_OUT = "EXTRA_OUT"
        lateinit var extraOut: String
        override fun onHandleIntent(p0: Intent?) {
            Thread.sleep(5000)
            extraOut = getJson(applicationContext, "irlix.json")!!
            val responseIntent = Intent()
            responseIntent.action = ACTION_MYINTENTSERVICE
            responseIntent.addCategory(Intent.CATEGORY_DEFAULT)
            responseIntent.putExtra(EXTRA_KEY_OUT, extraOut)
            sendBroadcast(responseIntent)
        }
    }
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

fun parseJson(json: String?): List<Something> {

    val gson = Gson()
    val listSomthingType = object : TypeToken<List<Something>>() {}.type
    return gson.fromJson(json, listSomthingType)
}