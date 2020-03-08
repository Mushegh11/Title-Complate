package com.mushegh.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.mushegh.myapplication.data.Repository

class UserViewModel(application : Application) : AndroidViewModel(application) {

        fun getUsers() = Repository.getUsers()


}