package com.mushegh.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.R
import com.mushegh.myapplication.data.models.Photo

class PhotoAdapter(var context : Context) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {

    private var photos : List<Photo> = listOf()
    private var mListener : AdapterView.OnItemClickListener? =null


    class PhotoViewHolder(item : View) : RecyclerView.ViewHolder(item)
    {

      //  var photo : ImageView = item.findViewById(R.id.photo_id)
        var textUrl : TextView = item.findViewById(R.id.user_text)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.users_item,parent,false)
        return PhotoViewHolder(v)
    }

    override fun getItemCount() = photos.size



    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        var currentPhoto = photos[position]



        holder.textUrl.text =  currentPhoto.url


    }
    fun setPhotos(photo : List<Photo>)
    {
        this.photos=photo
        notifyDataSetChanged()
    }

}
