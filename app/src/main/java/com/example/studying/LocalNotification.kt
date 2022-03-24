package com.example.studying

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class LocalNotification {
    fun sendNotification(context: Context, title: String, body: String) {
        val builder = NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_plaine)
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            ).setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0, Intent(),
                    PendingIntent.FLAG_IMMUTABLE
                )
            )
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(context)) {
            notify(42, builder.build())
        }
    }

}