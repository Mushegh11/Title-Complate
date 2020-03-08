package com.mushegh.myapplication.fragments

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mushegh.myapplication.R
import com.mushegh.myapplication.viewmodel.UserViewModel
import com.mushegh.myapplication.adapters.UsersAdapter
import com.mushegh.myapplication.data.Repository
import com.mushegh.myapplication.data.models.User
import kotlinx.android.synthetic.main.fragment_users.*

class UsersFragment : Fragment() , UsersAdapter.OnUsersItemClickListener {





    override fun OnClickUserItem(currentUser : User) {
        Log.d("TAG",currentUser.id.toString())
        var bundle : Bundle = Bundle()
        bundle.putInt("id",currentUser.id)
        var fragmentManager : FragmentManager = activity!!.supportFragmentManager
        var fragmentTrasaction : FragmentTransaction = fragmentManager.beginTransaction()

        var showfrag : AlbumsPhotosFragment = AlbumsPhotosFragment.newInstance()
        showfrag.arguments = bundle
        fragmentTrasaction.replace(R.id.fragment_container,showfrag,null).addToBackStack(null)
        fragmentTrasaction.commit()
    }

    var usersAdapter : UsersAdapter?=null
    var mLayoutManager : RecyclerView.LayoutManager?=null
    lateinit var userViewModel : UserViewModel



    companion object {
        fun newInstance() = UsersFragment()
    }








    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_users,container,false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        val recyclerView : RecyclerView = view.findViewById(R.id.recycler_view)
        mLayoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        usersAdapter = UsersAdapter(this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = usersAdapter




        users_swipeRefreshLayout.setOnRefreshListener {
            userViewModel.getUsers().observe(this, Observer {
                Log.d("TAG","Refresh")
                usersAdapter!!.setUsers(it)
                users_swipeRefreshLayout.isRefreshing = false
            })
        }




        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)








        userViewModel.getUsers().observe(this, Observer {
            usersAdapter!!.setUsers(it)
        })

    }


}