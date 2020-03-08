package com.mushegh.myapplication.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.mushegh.myapplication.R
import com.mushegh.myapplication.activities.MenuActivity
import com.mushegh.myapplication.adapters.ContactsAdapter
import com.mushegh.myapplication.data.Repository
import com.mushegh.myapplication.data.models.Contact
import com.mushegh.myapplication.viewmodel.ContactsViewModel
import kotlinx.android.synthetic.main.contacts_dialog.view.*
import java.lang.RuntimeException


class ContactsFragment : Fragment() , ContactsAdapter.OnAdapterItemClickListener {


    var listContacts : ArrayList<Contact>? = null

    override fun onItemLongClick(current: Contact, position: Int) {
       // Toast.makeText(context,"You clicked long $position",Toast.LENGTH_SHORT).show()
        var index = position
        val mDialogView = LayoutInflater.from(context).inflate(R.layout.contacts_dialog,null)
        val mBuilder = AlertDialog.Builder(context)
            .setView(mDialogView)
        //    .setTitle("!!!!!")
        val mAlertDialog = mBuilder.show()




        mDialogView.dialogEdit_button.setOnClickListener{

            var bundle = Bundle()
            bundle.putString("Name",current.firstName)
            bundle.putString("LastName",current.lastName)
            bundle.putString("Phone",current.number)
            bundle.putString("Email",current.email)
            bundle.putParcelable("image",current.image)
            bundle.putBoolean("Visibility",true)

            var fragmentManager : FragmentManager = activity!!.supportFragmentManager
            var fragmentTrasaction : FragmentTransaction = fragmentManager.beginTransaction()

            var editfrag : EditContactsFragment = EditContactsFragment.newInstance()
            editfrag.arguments = bundle
            fragmentTrasaction.replace(R.id.fragment_container,editfrag,null).addToBackStack(null)
            fragmentTrasaction.commit()
            mAlertDialog.dismiss()

        }



        mDialogView.dialogDelete_button.setOnClickListener{

           listContacts?.removeAt(index)
          //  contactsAdapter!!.notifyItemRemoved(index)
            contactsAdapter!!.setContacts(listContacts!!)
            mAlertDialog.dismiss()

        }
        mDialogView.dialogCancel_button.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }
    override fun onItemClick(current: Contact, position: Int) {


        var bundle : Bundle = Bundle()
        bundle.putString("Name",current.firstName)
        bundle.putString("LastName",current.lastName)
        bundle.putString("Phone",current.number)
        bundle.putString("Email",current.email)
        bundle.putParcelable("image",current.image)
        bundle.putBoolean("Visibility",false)

        var fragmentManager : FragmentManager = activity!!.supportFragmentManager
        var fragmentTrasaction : FragmentTransaction = fragmentManager.beginTransaction()

        var showfrag : EditContactsFragment = EditContactsFragment.newInstance()
        showfrag.arguments = bundle
        fragmentTrasaction.replace(R.id.fragment_container,showfrag,null).addToBackStack(null)
        fragmentTrasaction.commit()


    }

    lateinit var contactsViewModel :  ContactsViewModel
    var contactsAdapter : ContactsAdapter?=null
    var mLayoutManager : RecyclerView.LayoutManager?=null

    companion object {
        fun newInstance() = ContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.contacts_recyclerview,container,false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView : RecyclerView = view.findViewById(R.id.listcontacts)
        mLayoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        var listcontats : ArrayList<Contact> = ArrayList()

        contactsAdapter = ContactsAdapter(listcontats,context!!,this)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = contactsAdapter

        contactsViewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

//        if(Repository.PERMISSION_CONTACTS.value == true) {
//            contactsViewModel.getContacts(context!!).observe(this, Observer {
//
//                this.listContacts = ArrayList(it)
//                contactsAdapter!!.setContacts(it)
//            })
//        }
//        else{
//            var menuobj =   activity as MenuActivity
//            menuobj.requestStoragePermission()
//        }
        if ( ActivityCompat.checkSelfPermission(this.requireActivity()
                , Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            contactsViewModel.getContacts(context!!).observe(this, Observer {

                this.listContacts = ArrayList(it)
                contactsAdapter!!.setContacts(it)
            })

        } else {
            var menuobj =   activity as MenuActivity
            menuobj.requestStoragePermission()
        }

        Repository.PERMISSION_CONTACTS.observe(viewLifecycleOwner, Observer {
            if(it == true) {
                contactsViewModel.getContacts(context!!).observe(this, Observer {

                    this.listContacts = ArrayList(it)
                    contactsAdapter!!.setContacts(it)
                })
            }
        })
    }

}