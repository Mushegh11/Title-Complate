package com.mushegh.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mushegh.myapplication.data.Repository

class AlbumViewModel(application : Application) : AndroidViewModel(application) {
    fun getAlbums() = Repository.getAlbums()
    fun getAlbumsForUsers(id : Int) = Repository.getAlbumsForUsers(id)
}