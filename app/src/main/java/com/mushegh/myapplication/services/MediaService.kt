package com.mushegh.myapplication.services

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder

import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.mushegh.myapplication.MediaNotification.Companion.CHANNEL_ID

import com.mushegh.myapplication.data.models.Constants

import com.mushegh.myapplication.R
import com.mushegh.myapplication.activities.MenuActivity
import com.mushegh.myapplication.data.models.Media


class MediaService : Service() {

    lateinit var player: MediaPlayer
    var mBinder: IBinder = MediaBinder()
    var status = Constants.STATUS_STOP
    var currentPath: String? = null
    lateinit var currentMedia: Media
    var notificationCallBack: NotificationCallBack? = null


    lateinit var notificationManager: NotificationManagerCompat


    inner class MediaBinder : Binder() {

        fun play(media: Media): Constants {
            return this@MediaService.play(media)
        }

        fun setCallback(callBack: NotificationCallBack) {
            this@MediaService.notificationCallBack = callBack
        }

        fun getCurrentPath(): Pair<String?, Constants> {
            return Pair(currentPath, status)
        }

    }


    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }


    fun play(media: Media): Constants {

        currentMedia = media
        if (status == Constants.STATUS_STOP) {
            player = MediaPlayer.create(this, Uri.parse(media.path))
            player.start()
            status = Constants.STATUSE_PLAY
            notification(status, media)
            currentPath = media.path

        } else if (status == Constants.STATUSE_PLAY) {
            if (currentPath == media.path) {
                player.pause()
                status = Constants.STATUS_PAUSE
                notification(status, media)

            } else {
                player.reset()
                player.setDataSource(media.path)
                player.prepare()
                player.start()

                currentPath = media.path
                status = Constants.STATUSE_PLAY
                notification(status, media)


//                player.stop()
//                player = MediaPlayer.create(this, Uri.parse(media.path))
//                player.start()
//                status = Constants.STATUSE_PLAY
//                notification(status, media)
//                currentPath = media.path
//                currentState = true

            }
        } else if (status == Constants.STATUS_PAUSE) {
            if (currentPath == media.path) {
                player.start()
                status = Constants.STATUSE_PLAY
                notification(status, media)

            } else {

                player.reset()
                player.setDataSource(media.path)
                player.prepare()
                player.start()
                currentPath = media.path
                status = Constants.STATUSE_PLAY
                notification(status, media)


//                player.stop()
//                player = MediaPlayer.create(this, Uri.parse(media.path))
//                player.start()
//                status = Constants.STATUSE_PLAY
//                notification(status, media)
//                currentPath = media.path
//                currentState = true
            }
        }
        return status
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {


        if (intent!!.action == Constants.STATUS_KILL.toString()) {
            notificationCallBack?.unbindService()
            status = Constants.STATUS_STOP
            notificationCallBack!!.changeItemState(status)
            stopSelf()
            player.stop()
            //player.release()

        } else if (intent.action == Constants.STATUS_PLAYER.toString()) {
            if (status == Constants.STATUS_PAUSE) {
                player.start()

                status = Constants.STATUSE_PLAY

                notificationCallBack!!.changeItemState(status)
                notification(status, currentMedia)
            } else if (status == Constants.STATUSE_PLAY) {
                player.pause()
                status = Constants.STATUS_PAUSE

                notificationCallBack!!.changeItemState(status)
                notification(status, currentMedia)
            }

        }

        return START_NOT_STICKY
    }


    fun notification(status: Constants, media: Media) {
        notificationManager = NotificationManagerCompat.from(this)
        var collapsedView = RemoteViews(packageName, R.layout.notification_collapsed)
        var expandedView = RemoteViews(packageName, R.layout.notification_expanded)


        val clickIntent = Intent(this, MediaService::class.java)
        clickIntent.action = Constants.STATUS_KILL.toString()
        val clickPendingIntent = PendingIntent.getService(this, 0, clickIntent, 0)
        val clickIntent1 = Intent(this, MediaService::class.java)
        clickIntent1.action = Constants.STATUS_PLAYER.toString()
        val clickPendingIntent1 = PendingIntent.getService(this, 0, clickIntent1, 0)

        val clickIntent2 = Intent(this, MenuActivity::class.java)
        clickIntent2.action = "Fragment"
        val clickPendingIntent2 = PendingIntent.getActivity(this, 0, clickIntent2, 0)


        expandedView.setTextViewText(R.id.notification_media_title, media.name)
        collapsedView.setTextViewText(R.id.notif_media_name, media.name)

        if (status == Constants.STATUS_PAUSE) {
            expandedView.setImageViewResource(
                R.id.notification_media_state,
                R.drawable.ic_play_arrow_black_24dp
            )
        } else if (status == Constants.STATUSE_PLAY) {
            expandedView.setImageViewResource(
                R.id.notification_media_state,
                R.drawable.ic_pause_black_24dp
            )
        }

        expandedView.setOnClickPendingIntent(R.id.notification_killbutton, clickPendingIntent)
        expandedView.setOnClickPendingIntent(R.id.notification_media_state, clickPendingIntent1)

        var notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_music_note_black_24dp)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setAutoCancel(true)
            .setCustomBigContentView(expandedView)
            .setCustomContentView(collapsedView)
            .setContentIntent(clickPendingIntent2)
            .build()



        startForeground(1, notification)


    }


    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }

    interface NotificationCallBack {
        fun unbindService()
        fun changeItemState(currentState: Constants)
    }

}