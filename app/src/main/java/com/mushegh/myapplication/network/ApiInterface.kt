package com.mushegh.myapplication.network

import android.content.ContentResolver
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import com.mushegh.myapplication.data.models.Album
import com.mushegh.myapplication.data.models.Contact
import com.mushegh.myapplication.data.models.Photo
import com.mushegh.myapplication.data.models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/users")
    fun getUser() : Call<List<User>>

    @GET( "/albums")
    fun getAlbums() : Call<List<Album>>

    @GET("/photos")
    fun getPhotos(@Query("albumId") albumId : Int) : Call<List<Photo>>

    @GET("/albums")
    fun getAlbumsForUsers(@Query("userId") userId: Int) : Call<List<Album>>

}