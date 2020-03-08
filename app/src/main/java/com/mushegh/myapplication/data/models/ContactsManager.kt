package com.mushegh.myapplication.data.models

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.MutableLiveData
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import retrofit2.Call

object ContactsManager {







    fun getContacts(context: Context): MutableLiveData<List<Contact>> {
        val liveData = MutableLiveData<List<Contact>>()


//        var permissionListener = object  : PermissionListener {
//            override fun onPermissionGranted() {



                var contactsList: ArrayList<Contact> = ArrayList()
                var mapEmails: MutableMap<String, String> = mutableMapOf()


//        var listEMails : MutableList<String> = ArrayList()
//        var i=0
                val cr = context.contentResolver
                val cursor1 = cr?.query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
                )
                if (cursor1 != null && cursor1.moveToFirst()) {
                    do {

                        var email: String? =
                            cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))

                        val id =
                            cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Identity.CONTACT_ID))
                        if (!email.isNullOrEmpty()) {
                            mapEmails.put(id, email)
                        } else {
                            mapEmails.put(id, "EMail Not Found")
                        }



                    } while (cursor1.moveToNext())
                    cursor1.close()
                }


                val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                val projection: Array<String>? = null
                val selection: String? = null
                val selectionArgs: Array<String>? = null
                val sortOrder: String? = null
                var resolver: ContentResolver = context.contentResolver
                var cursor: Cursor? =
                    resolver.query(uri, projection, selection, selectionArgs, sortOrder)
                while (cursor!!.moveToNext()) {
                    var name: String =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    var number =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val id =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))

                    var obj: Contact = Contact()
                    obj.firstName = name
                    obj.number = number

                    obj.email = mapEmails.get(id)


                    val photouri =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                    if (photouri != null) {
                        obj.image =
                            MediaStore.Images.Media.getBitmap(
                                context.contentResolver,
                                Uri.parse(photouri)
                            )
                    }

                    contactsList.add(obj)



                    liveData.value = contactsList
                    // listcontacts.adapter = ContactsAdapter(contactsList, context)


                }
                cursor.close()







//            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//
//            }

        

//        TedPermission.with(context)
//            .setPermissionListener(permissionListener)
//            .setPermissions(Manifest.permission.READ_CONTACTS)
//            .check()


      //  Manifest.permission.READ_EXTERNAL_STORAGE

        return liveData
    }
}

