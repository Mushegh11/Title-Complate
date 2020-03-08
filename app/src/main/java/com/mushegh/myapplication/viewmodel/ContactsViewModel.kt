package com.mushegh.myapplication.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.mushegh.myapplication.data.Repository

class ContactsViewModel (application : Application) : AndroidViewModel(application) {


        fun getContacts(context: Context) = Repository.getContacts(context)


}
