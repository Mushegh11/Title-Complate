package com.mushegh.myapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.*
import com.mushegh.myapplication.adapters.AlbumsRecyclerAdapter
import com.mushegh.myapplication.data.Repository
import com.mushegh.myapplication.data.models.Album
import com.mushegh.myapplication.viewmodel.AlbumViewModel
import kotlinx.android.synthetic.main.albumphoto_layout.*
import kotlinx.android.synthetic.main.fragment_users.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlbumsPhotosFragment : Fragment() {


    var albumsAdapter : AlbumsRecyclerAdapter?=null
    var mLayoutManager : RecyclerView.LayoutManager?=null
    lateinit var albumViewModel : AlbumViewModel
     var list : ArrayList<Album> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view = inflater.inflate(R.layout.albumphoto_layout,container,false)


        return view
    }



    companion object {
        fun newInstance() = AlbumsPhotosFragment()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)





        mLayoutManager = LinearLayoutManager(context)
        ParentRecView.setHasFixedSize(true)
        albumsAdapter = AlbumsRecyclerAdapter(list, context!!)
        ParentRecView.layoutManager = mLayoutManager
        ParentRecView.adapter = albumsAdapter





        albumViewModel = ViewModelProvider(this).get(AlbumViewModel::class.java)


        if (arguments != null && (arguments!!).containsKey("id")) {

            var bundle : Bundle = arguments!!

            if(!bundle.isEmpty)
            {
                var id = bundle.getInt("id")
                albums_photos_swipeRefresh.isRefreshing = true
                albumViewModel.getAlbumsForUsers(id).observe(this, Observer {

                    albumsAdapter!!.setAlbums(it)
                   albums_photos_swipeRefresh.isRefreshing = false

                })
                arguments = null


                albums_photos_swipeRefresh.setOnRefreshListener {
                    albumViewModel.getAlbumsForUsers(id).observe(this, Observer {

                        Log.d("TAG","AlbumsRefresh")
                        albumsAdapter!!.setAlbums(it)
                        albums_photos_swipeRefresh.isRefreshing = false
                    })
                }

            }

        }



        else
        {

            albums_photos_swipeRefresh.setOnRefreshListener {
                albumViewModel.getAlbums().observe(this, Observer {

                    Log.d("TAG","AlbumsRefresh")
                    albumsAdapter!!.setAlbums(it)
                    albums_photos_swipeRefresh.isRefreshing = false
                })
            }

            albums_photos_swipeRefresh.isRefreshing = true
            albumViewModel.getAlbums().observe(this, Observer {

            albumsAdapter!!.setAlbums(it)
                albums_photos_swipeRefresh.isRefreshing = false
        })
            }


    }
}
