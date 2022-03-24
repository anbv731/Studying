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

        val builder = NotificationCompat.Builder(this, MainActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_plaine)
            .setContentTitle(message.notification?.title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message.notification?.body)
            ).setContentIntent(
                PendingIntent.getActivity(
                    applicationContext,
                    0, Intent(),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(this)) {
            notify(42, builder.build())
        }
    }
}