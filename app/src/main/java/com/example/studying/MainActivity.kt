package com.example.studying

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.example.studying.databinding.ActivityMainBinding
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.tasks.OnCompleteListener
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
            globalNotification()
        }
    }
    private fun globalNotification(){
        checkGoogleServices()
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task->
            if (task.isSuccessful) {
                return@OnCompleteListener
            }
            val token=task.result
            Toast.makeText(baseContext,token,Toast.LENGTH_SHORT).show()
        })
    }
   private fun localNotification(){
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_plaine)
            .setContentText("Вот такое вот уведомление")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("ООООООоооооооооооооооооооооооооооооооооочень длинно")
            ).setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)){
            notify(42,builder.build())
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
   private fun checkGoogleServices ():Boolean{
        val status=GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
         if (status !=ConnectionResult.SUCCESS){
             println("Отсутствуют Google сервисы")
             return false
        }else{
             println("Присутствуют Google сервисы")
            return true
        }
    }
}