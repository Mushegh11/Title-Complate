package com.mushegh.myapplication.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.mushegh.myapplication.R
import com.mushegh.myapplication.data.Consts
import com.mushegh.myapplication.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()  {


    private lateinit var mCManager : ConnectivityManager
    private var mCallback : ConnectivityManager.NetworkCallback?=null

    private var b =false

    private var viewModel : UserViewModel?=null

//    override fun onRestart() {
//        super.onRestart()
//        if(Consts.KILL_CONSTANT)
//        {
//            finish()
//        }
//    }

    private fun isConnected(): Boolean
    {
        return b
    }


    private fun validateEmail() : Boolean {
        var emailInput = email_text.text.toString().trim()
        if(emailInput.isEmpty())
        {
            email_text.setError("Field can't be empty")
            return false
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())
        {
            email_text.setError("Please enter a valid email adress")
            return false
        }
        else {
            return true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startActivity(Intent(this, MenuActivity::class.java))
        finish()


        password_text.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                val password_string = password_text.text.toString()
                if(password_string.length<6)
                {
                    password_text.setError("Error: Password length <6")
                }
            }
        })
        mCManager =  getSystemService(Context.CONNECTIVITY_SERVICE) as (ConnectivityManager)

        mCallback = object : ConnectivityManager.NetworkCallback(){

            override fun onLost(network: Network?) {
                b=false
            }

            override fun onAvailable(network: Network?) {
                b=true
            }
        }

//        viewModel =ViewModelProviders.of(this).get(UserViewModel::class.java)
//
//        viewModel.getUsers().observe(this, Observer<List<User>> {
//
//        })

    }

    override fun onStart() {
        super.onStart()
        var request : NetworkRequest  =  NetworkRequest.Builder().build()
        mCManager.registerNetworkCallback(request,mCallback)
    }

     fun onClcikSignIn(view: View)
    {
        if(validateEmail() && isConnected())
        {
            Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
            var intent : Intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
        if(!isConnected())
        {
            Toast.makeText(this,"Please turn on internet connection",Toast.LENGTH_LONG).show()
        }
    }

    override fun onPause() {
        super.onPause()
        mCManager.unregisterNetworkCallback(mCallback)
    }

}
