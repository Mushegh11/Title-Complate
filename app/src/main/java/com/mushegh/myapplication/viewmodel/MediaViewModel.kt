package com.mushegh.myapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.mushegh.myapplication.data.Repository

class MediaViewModel(application: Application) : AndroidViewModel(application) {
    fun getMusicList(context: Context) = Repository.getMusicList(context)
}