package com.mushegh.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.R
import com.mushegh.myapplication.data.models.Album

class AlbumsAdapter : RecyclerView.Adapter<AlbumsAdapter.AlbumsViewHolder>() {

    private var albums : List<Album> = listOf()

    class AlbumsViewHolder(item : View) : RecyclerView.ViewHolder(item)
    {

        var textViewName : TextView = item.findViewById(R.id.user_text)


    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlbumsViewHolder {

        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.users_item,parent,false)
        return AlbumsViewHolder(v)

    }

    override fun getItemCount() = albums.size



    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {

        var currentalbum = albums[position]



        holder.textViewName.text =  currentalbum.id.toString()

    }

    fun setAlbums(albums : List<Album>)
    {
        this.albums=albums
        notifyDataSetChanged()
    }

}