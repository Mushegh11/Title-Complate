package com.mushegh.myapplication.fragments

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.R
import com.mushegh.myapplication.activities.MenuActivity
import com.mushegh.myapplication.adapters.MediaAdapter
import com.mushegh.myapplication.data.Repository
import com.mushegh.myapplication.data.models.Media
import com.mushegh.myapplication.services.MediaService
import com.mushegh.myapplication.viewmodel.MediaViewModel
import android.app.ActivityManager
import com.mushegh.myapplication.data.models.Constants
import kotlinx.android.synthetic.main.fragment_media.*


class MediaFragment : Fragment(), MediaAdapter.OnMediaItemClickListener,
    MediaService.NotificationCallBack {

    var isBound: Boolean = false
    var currentSong: Media? = null


    override fun unbindService() {
        if (isBound) {
            activity?.unbindService(mConnection)
            isBound = false
        }
    }

    override fun changeItemState(currentState: Constants) {

        mediasAdapter.currentStateMedia = currentState
        mediasAdapter.notifyDataSetChanged()

    }

    lateinit var binder: MediaService.MediaBinder

    var listMedia: ArrayList<Media>? = null

    lateinit var mediasViewModel: MediaViewModel
    lateinit var mediasAdapter: MediaAdapter
    var mLayoutManager: RecyclerView.LayoutManager? = null


    var mConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as MediaService.MediaBinder
            binder.setCallback(this@MediaFragment)
            isBound = true
            if (currentSong == null) {
                binder.getCurrentPath()
                //val status = binder.play(binder.getCurrentPath() ?: Media())
                mediasAdapter.current = binder.getCurrentPath().first

                mediasAdapter.currentStateMedia = binder.getCurrentPath().second
                mediasAdapter.notifyDataSetChanged()

            } else {
                val status = binder.play(currentSong ?: Media())
                mediasAdapter.current = currentSong?.path

                mediasAdapter.currentStateMedia = status
                mediasAdapter.notifyDataSetChanged()

            }

        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(context, MediaService::class.java)

//        activity!!.startForegroundService(intent)
//        bindService()
        if (isMyServiceRunning(MediaService::class.java.name)) {
            bindService()
        }

    }

    @Suppress("DEPRECATION")
    private fun isMyServiceRunning(serviceClassName: String): Boolean {
        val manager = activity!!.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClassName == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun bindService() {
        if (!isBound) {
            val intent = Intent(context, MediaService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                activity!!.startForegroundService(intent)
            } else {
                activity!!.startService(intent)
            }
            activity!!.bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }

    }

    override fun onItemClick(listMedia: ArrayList<Media>, position: Int) {


        if (!isBound) {
            bindService()
        }
        currentSong = listMedia[position]
        if (isBound) {
            val status: Constants = binder.play(listMedia[position])

            mediasAdapter.current = listMedia[position].path

            mediasAdapter.currentStateMedia = status

            mediasAdapter.notifyDataSetChanged()

        }
    }


    companion object {
        fun newInstance() = MediaFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v: View = inflater.inflate(R.layout.fragment_media, container, false)

        return v
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mLayoutManager = LinearLayoutManager(context)
        media_recyclerview.setHasFixedSize(true)

        val listcontats: ArrayList<Media> = ArrayList()

        mediasAdapter = MediaAdapter(listcontats, requireContext(), this)
        media_recyclerview.layoutManager = mLayoutManager
        media_recyclerview.adapter = mediasAdapter
        mediasViewModel = ViewModelProvider(this)[MediaViewModel::class.java]


        if (ActivityCompat.checkSelfPermission(
                this.requireActivity()
                , Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mediasViewModel.getMusicList(requireContext()).observe(this, Observer {

                this.listMedia = ArrayList(it)
                mediasAdapter.setMedia(it)
            })

        } else {
            (activity as MenuActivity).requestStoragePermissionMedia()
        }

        Repository.PERMISSION_MEDIA.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                mediasViewModel.getMusicList(context!!).observe(this, Observer {

                    this.listMedia = ArrayList(it)
                    mediasAdapter.setMedia(it)
                })
            }
        })


    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound == true) {
            activity?.unbindService(mConnection)
            isBound = false
        }
        // activity!!.stopService(intent)
    }

}