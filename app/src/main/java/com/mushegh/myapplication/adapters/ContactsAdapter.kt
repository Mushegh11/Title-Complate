package com.mushegh.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.R
import com.mushegh.myapplication.data.models.Contact

class ContactsAdapter(items : ArrayList<Contact>, context : Context , interfaceClick : OnAdapterItemClickListener) : RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {

    private var contacts : List<Contact> = items
    private var context : Context = context
    var listener: OnAdapterItemClickListener? = null
    init {
        listener = interfaceClick
    }

    class ContactsViewHolder(item : View) : RecyclerView.ViewHolder(item)
    {

//        override fun onCreateContextMenu(
//            menu: ContextMenu?,
//            v: View?,
//            menuInfo: ContextMenu.ContextMenuInfo?
//        ) {
//            menu!!.add(this.adapterPosition,121,0,"Edit")
//            menu.add(this.adapterPosition,122,1,"Delete")
//        }

        var textViewName: TextView = item.findViewById(R.id.contacts_text)
        var profile: ImageView = item.findViewById(R.id.image_contact)



        var linearLayout: LinearLayout = item.findViewById(R.id.linearlayoutContacts)
        init {

//            linearLayout.setOnCreateContextMenuListener(this)
        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val v : View = LayoutInflater.from(context).inflate(R.layout.contacts_listcard,parent,false)
        return ContactsViewHolder(v)
    }

    override fun getItemCount() = contacts.size



    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        var currentUser = contacts[position]

        //   val user : User = users[position]

        holder.textViewName.text =  currentUser.firstName
        if(currentUser.image!=null)
        {
            holder.profile.setImageBitmap(currentUser.image)
        }
        else
        {
            holder.profile.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_account_circle_black_24dp))
        }
        //listUsers[position].username




        holder.linearLayout.setOnClickListener()
        {
            listener?.onItemClick(currentUser,position)
        }
       holder.linearLayout.setOnLongClickListener {
               listener?.onItemLongClick(currentUser ,position)
            return@setOnLongClickListener true

        }






//        holder.linearLayout.setOnClickListener()
//        {
//            Toast.makeText(context,"You clicked $position",Toast.LENGTH_SHORT).show()
//
//            var intent : Intent = Intent(context, ShowContactsFragment::class.java)
//            intent.putExtra("Name",currentUser.firstName)
//            intent.putExtra("Phone",currentUser.number)
//            intent.putExtra("Email",currentUser.email)
//            intent.putExtra("image",currentUser.image)
//            context.startActivity(intent)
//
//        }




    }
    fun setContacts(contacts : List<Contact>)
    {
        this.contacts=contacts
        notifyDataSetChanged()
    }

    fun setContacts(contacts : ArrayList<Contact>)
    {
        this.contacts=contacts
        notifyDataSetChanged()
    }

    interface OnAdapterItemClickListener{
        fun onItemClick(current : Contact,position : Int)
        fun onItemLongClick(current: Contact, position : Int)
    }
}