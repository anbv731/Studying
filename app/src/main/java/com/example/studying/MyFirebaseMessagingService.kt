package com.example.studying

import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage
import java.security.AccessController.getContext

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.i(
            "Messaging Service",
            message.notification?.title!! + " " + message.notification?.body!!
        )
        LocalNotification().sendNotification(this,message.notification?.title!!,message.notification?.body!!)
    }
}