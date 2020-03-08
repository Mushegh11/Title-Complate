package com.mushegh.myapplication.data.models

import android.Manifest
import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission


object MediaManager {


    fun getMusicList(context: Context): MutableLiveData<List<Media>> {
        var musiclist: ArrayList<Media> = ArrayList()
        val liveData = MutableLiveData<List<Media>>()


        val projection = arrayOf(
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media._ID
        )

        val cr = context.contentResolver
        val where = MediaStore.Audio.Media.DATA + " like ? "
        var cursor = cr?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            where,
            arrayOf("%Practice media%"),
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                val name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                val album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))

                val obj = Media()
                obj.artist = artist
                obj.album = album
                obj.id = id
                obj.name = name
                obj.path = path
                obj.type = "audio"



                musiclist.add(obj)
            } while (cursor.moveToNext())
            // cursor.close()
        }


        val projection1 = arrayOf(
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.ARTIST,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.ALBUM,
            MediaStore.Video.Media._ID
        )


        val where1 = MediaStore.Audio.Media.DATA + " like ? "
        cursor = context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection1,
            where1,
            arrayOf("%Practice media%"),
            null
        )
        if (cursor != null && cursor.moveToFirst()) {
            do {
                val path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA))
                val name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE))
                val album = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ALBUM))
                val artist = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST))
                val id = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media._ID))

                var obj: Media = Media()
                obj.artist = artist
                obj.album = album
                obj.id = id
                obj.name = name
                obj.path = path
                obj.type = "video"

                musiclist.add(obj)
                liveData.value = musiclist

            } while (cursor.moveToNext())
            cursor.close()
        }


//    TedPermission.with(context)
//        .setPermissionListener(permissionListener)
//        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
//        .check()

        return liveData


    }

}