package com.mushegh.myapplication.data.models

import androidx.lifecycle.MutableLiveData

 class User(var id : Int , var username :String, var email :String) {
    override fun toString(): String {
        return  "$id  $username  $email"
    }


}