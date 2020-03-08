package com.mushegh.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.R
import com.mushegh.myapplication.data.models.Photo
import com.squareup.picasso.Picasso

class PhotoRecyclerAdapter : RecyclerView.Adapter<PhotoRecyclerAdapter.MyViewHolder> {

//list photo
     var arrayPhoto : List<Photo>
    var context: Context? = null
    constructor(arrayList : List<Photo>  ) {
        this.arrayPhoto = arrayList

    }

    @NonNull
    @Override
    public override fun  onCreateViewHolder(@NonNull parent : ViewGroup, viewType : Int) : MyViewHolder {
      var view : View  = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_rowlayout,parent,false)
        context = view.context
        return MyViewHolder(view)
    }

    @Override
    public override fun onBindViewHolder(@NonNull holder : MyViewHolder, position : Int) {
        holder.setPhoto(arrayPhoto[position].url)
    }

    @Override
  override fun getItemCount() = arrayPhoto.size


     class MyViewHolder : RecyclerView.ViewHolder {
       var Photos : ImageView
        constructor(itemView :View ) :  super(itemView) {

            Photos = itemView.findViewById(R.id.photo_image)
            Photos.setImageResource(R.drawable.ic_cached_black_24dp)
        }

         fun setPhoto(url: String){
             Picasso.get().load(url).placeholder(R.drawable.ic_cached_black_24dp).into(Photos)
         }

    }
}

