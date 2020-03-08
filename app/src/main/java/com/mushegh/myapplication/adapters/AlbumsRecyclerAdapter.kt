package com.mushegh.myapplication.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.R
import com.mushegh.myapplication.data.Repository
import com.mushegh.myapplication.data.models.Album
import com.mushegh.myapplication.data.models.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumsRecyclerAdapter(
    var  AlbumList : List<Album>,
    var context: Context
) : RecyclerView.Adapter<AlbumsRecyclerAdapter.MyViewHolder>() {




    fun setAlbums(users : List<Album>)
    {
        this.AlbumList=users
        notifyDataSetChanged()
    }


    @NonNull
    @Override
    override fun  onCreateViewHolder(@NonNull parent : ViewGroup, viewType : Int) : MyViewHolder {
     var view : View = LayoutInflater.from(parent.getContext()).inflate(R.layout.parent_rowlayout,parent,false)
        return MyViewHolder(view)
    }

    @Override
    override fun onBindViewHolder(@NonNull holder : MyViewHolder, position : Int) {
        holder.ItemName.setText(AlbumList[position].title)
     var  layoutManager : RecyclerView.LayoutManager  = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false )
        holder.ChildRV.setLayoutManager(layoutManager)
        holder.ChildRV.setHasFixedSize(true)
        var childRecycleAdapter1  = PhotoRecyclerAdapter(arrayListOf())
        holder.ChildRV.setAdapter(childRecycleAdapter1)



        Repository.apiInterface.getPhotos(AlbumList[position].id).enqueue(object  :
            Callback<List<Photo>>
        {
            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.d("TAG", "OnFailure" + t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                var list : List<Photo>? = response.body()
                var  layoutManager1 : RecyclerView.LayoutManager  = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false )
                holder.ChildRV.setLayoutManager(layoutManager1)
                holder.ChildRV.setHasFixedSize(true)
                var childRecycleAdapter  =
                    PhotoRecyclerAdapter(list!!)
                holder.ChildRV.setAdapter(childRecycleAdapter)
                childRecycleAdapter.notifyDataSetChanged()
            }


        })
//        var childRecycleAdapter : PhotoRecyclerAdapter =  PhotoRecyclerAdapter(AlbumList)
//        holder.ChildRV.setAdapter(childRecycleAdapter)
//        childRecycleAdapter.notifyDataSetChanged()
    }

    @Override
    override fun  getItemCount() =AlbumList.size



     class MyViewHolder : RecyclerView.ViewHolder {

          var ItemName: TextView
          var ChildRV: RecyclerView
        lateinit var photoAdapter : PhotoRecyclerAdapter
         //photo adapter

         constructor(itemView: View) : super(itemView) {
             ItemName = itemView.findViewById(R.id.itemname)
             ChildRV = itemView.findViewById(R.id.childRecView)
         }

     }
}