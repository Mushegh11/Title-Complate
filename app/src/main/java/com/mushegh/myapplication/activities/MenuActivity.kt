package com.mushegh.myapplication.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView

import com.mushegh.myapplication.data.Repository

import com.mushegh.myapplication.fragments.AlbumsPhotosFragment
import com.mushegh.myapplication.fragments.ContactsFragment
import com.mushegh.myapplication.fragments.MediaFragment
import com.mushegh.myapplication.fragments.UsersFragment
import kotlinx.android.synthetic.main.activity_menu.*


import androidx.fragment.app.Fragment

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T






class MenuActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

var permissioncode : Int = 1
    var mediapermissioncode : Int =2
    var bool : Boolean = true


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if(item.itemId == com.mushegh.myapplication.R.id.nav_users)
        {

            supportFragmentManager.beginTransaction().replace(com.mushegh.myapplication.R.id.fragment_container,UsersFragment.newInstance(),"User").addToBackStack(null).commit()
            setTitle("Users")
        }
        else if(item.itemId == com.mushegh.myapplication.R.id.Page1)
        {

            supportFragmentManager.beginTransaction().replace(com.mushegh.myapplication.R.id.fragment_container,AlbumsPhotosFragment.newInstance(),"Album").addToBackStack(null).commit()
            setTitle("Albums")
        }
        else if(item.itemId == com.mushegh.myapplication.R.id.Page2)
        {

            supportFragmentManager.beginTransaction().replace(com.mushegh.myapplication.R.id.fragment_container,ContactsFragment.newInstance(),"Contact").addToBackStack(null).commit()
            setTitle("Contacts")

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS)==PackageManager.PERMISSION_GRANTED)
            {
                Repository.PERMISSION_CONTACTS.value = true
            }
            else
            {
                requestStoragePermission()
            }

        }
        else if(item.itemId == com.mushegh.myapplication.R.id.nav_media)
        {

          supportFragmentManager.beginTransaction().replace(com.mushegh.myapplication.R.id.fragment_container,MediaFragment.newInstance(),"Media").addToBackStack(null).commit()
            setTitle("Media")

            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
            {
                Repository.PERMISSION_MEDIA.value = true
            }
            else
            {
                requestStoragePermissionMedia()
            }


        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    private fun getCurrentFragment(): Fragment {
        val fragmentManager = supportFragmentManager
        val fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.backStackEntryCount - 1).name
        return fragmentManager.findFragmentByTag(fragmentTag)!!
    }


    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        }


        supportFragmentManager.popBackStackImmediate()
        val list : MutableList<Fragment> = supportFragmentManager.fragments
        var fragment = list[0]



            if(fragment is MediaFragment)
            {
                setTitle("Media")
                return
            }
         else   if(fragment is UsersFragment)
            {
                setTitle("Users")
                return


            }
          else  if(fragment is AlbumsPhotosFragment)
            {
                setTitle("Albums")
                return


            }
         else   if(fragment is ContactsFragment)
            {
                setTitle("Contacts")
                return


            }







        if(drawer_layout.isDrawerOpen(GravityCompat.START))
        {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        else
        {
            super.onBackPressed()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mushegh.myapplication.R.layout.activity_menu)

        if (intent.action =="Fragment") {
            setTitle("Media")
            supportFragmentManager.beginTransaction()
                .replace(com.mushegh.myapplication.R.id.fragment_container, MediaFragment.newInstance()).addToBackStack(null).commit()
        } else {
            if(savedInstanceState == null) {
                setTitle("Users")
                supportFragmentManager.beginTransaction()
                    .replace(com.mushegh.myapplication.R.id.fragment_container, UsersFragment()).commit()

                nav_view.setCheckedItem(com.mushegh.myapplication.R.id.nav_users)
            }

        }
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this,drawer_layout,toolbar,
           com.mushegh.myapplication.R.string.navigation_drawer_open,
           com.mushegh.myapplication.R.string.navigation_drawer_close
       )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()



        nav_view.setNavigationItemSelectedListener(this)








    }




    fun requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS))
        {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS) , permissioncode)
        }
        else
        {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS) , permissioncode)
        }
    }



    fun requestStoragePermissionMedia() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) , mediapermissioncode)
        }
        else
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE) , mediapermissioncode)
        }
    }




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == permissioncode) {
            Repository.PERMISSION_CONTACTS.value = grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        else if(requestCode == mediapermissioncode)
        {
            Repository.PERMISSION_MEDIA.value= grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
        }

    }




}
