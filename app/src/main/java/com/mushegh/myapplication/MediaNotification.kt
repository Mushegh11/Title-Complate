package com.mushegh.myapplication

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class MediaNotification : Application() {

    companion object {
        var CHANNEL_ID: String = "MediaServiceChannel"
    }
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    fun createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var serviceChanel = NotificationChannel(CHANNEL_ID,"Media Service Channel",NotificationManager.IMPORTANCE_LOW)
            var manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChanel)
        }



    }
}