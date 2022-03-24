package com.example.studying

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.example.studying.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {
    companion object {
        val CHANNEL_ID = "CHANNEL_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        createNotificationChannel()
        binding.buttonId.setOnClickListener {
            getToken()
        }
    }

    private fun getToken() {
        if (checkGoogleServices()) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                } else {
                    println("Удалось получить токен" + task.toString())
                }
                val token = task.result
                println("XX>" + token.toString() + "<XX")
                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun createNotificationChannel() {
        val name = "channel_name"
        val descriptionText = "channel_description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun checkGoogleServices(): Boolean {
        val status = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (status != ConnectionResult.SUCCESS) {
            println("Отсутствуют Google сервисы")
            return false
        } else {
            println("Присутствуют Google сервисы")
            return true
        }
    }
}