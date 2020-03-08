package com.mushegh.myapplication.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.mushegh.myapplication.R
import kotlinx.android.synthetic.main.editcontacts.*


class EditContactsFragment : Fragment() {


    companion object{
        fun newInstance() = EditContactsFragment().apply {  }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var v : View = inflater.inflate(R.layout.editcontacts , container,false)



        return v

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.save_menu)
        {
            edit_contactsFirstName.isEnabled = false
            edit_contactsLastName.isEnabled = false
            edit_contactsEmail.isEnabled = false
            edit_contactsPhone.isEnabled = false
            edit_image_contact.isEnabled = false
        }
        return super.onOptionsItemSelected(item)

    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.savecontacts_menu,menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bundle : Bundle = arguments!!
        var name = bundle.getString("Name")
        var phone = bundle.getString("Phone")
        var email = bundle.getString("Email")

        var firstName : String = ""
        var lastName : String = ""

        var i=0

        while(i<name!!.length)
        {
            if(name[i]!=' ')
            {
                firstName+=name[i]
            }
            else
            {
                var j =i+1
               // lastName+=name[j]
                while(j<name.length)
                {

                    lastName+=name[j]
                    j++
                }
                break
            }
            i++
        }
        edit_contactsFirstName.setText(firstName)
        edit_contactsLastName.setText (lastName)
        if(phone!=null) {
            edit_contactsPhone.setText ( phone)
        }
        else{
            edit_contactsPhone.setText("Phone not found")
        }
        if(email!=null) {
            edit_contactsEmail.setText ( email)
        }

        var image = bundle.getParcelable<Bitmap>("image")
        if(image != null) {
            edit_image_contact.setImageBitmap(image)
        }
        else
        {
            edit_image_contact.setImageResource(R.drawable.ic_account_circle_black_24dp)
        }


        if(!bundle.getBoolean("Visibility"))
        {
            edit_contactsFirstName.isEnabled = false
            edit_contactsLastName.isEnabled = false
            edit_contactsEmail.isEnabled = false
            edit_contactsPhone.isEnabled = false
            edit_image_contact.isEnabled = false
            setHasOptionsMenu(false)
        }


        else if (bundle.getBoolean("Visibility"))
        {
            edit_contactsFirstName.isEnabled = true
            edit_contactsLastName.isEnabled = true
            edit_contactsEmail.isEnabled = true
            edit_contactsPhone.isEnabled = true
            edit_image_contact.isEnabled = true
            setHasOptionsMenu(true)
        }




    }


}