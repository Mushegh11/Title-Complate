package com.mushegh.myapplication.adapters

import android.content.Context
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.mushegh.myapplication.R
import com.mushegh.myapplication.data.models.Constants
import com.mushegh.myapplication.data.models.Contact
import com.mushegh.myapplication.data.models.Media


class MediaAdapter(items : ArrayList<Media>,  context : Context , interfaceClick : OnMediaItemClickListener) : RecyclerView.Adapter<MediaAdapter.MediaViewHolder>() {

    private var medias : List<Media> = items
    var current : String? = null
    private var context : Context = context
    var currentStateMedia : Constants = Constants.STATUS_STOP

    var listener: OnMediaItemClickListener? = null
    init {
        listener = interfaceClick
    }

    class MediaViewHolder(item : View) : RecyclerView.ViewHolder(item)
    {

        var mediaName : TextView = item.findViewById(R.id.media_name)
        var mediaType : ImageView = item.findViewById(R.id.media_type)
        var mediaplay : ImageView = item.findViewById(R.id.play_music)
        var relativeLayout: RelativeLayout = item.findViewById(R.id.media_RelativeLayout)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val v : View = LayoutInflater.from(parent.context).inflate(R.layout.media_item,parent,false)
        return MediaViewHolder(v)
    }

    override fun getItemCount() = medias.size

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val currentMedia = medias[position]
        holder.mediaName.text = currentMedia.name



        if (current == currentMedia.path)
        {
            holder.mediaplay.visibility = View.VISIBLE
           if(currentStateMedia == Constants.STATUSE_PLAY){

                holder.mediaplay.setImageResource(R.drawable.ic_pause_black_24dp)


                }
            else if ( currentStateMedia == Constants.STATUS_PAUSE)
           {

               holder.mediaplay.setImageResource(R.drawable.ic_play_arrow_black_24dp)


           }


           // notifyItemChanged(position)
        } else {
            holder.mediaplay.visibility = View.INVISIBLE
        }


        if(currentMedia.type == "audio")
        {
            holder.mediaType.setImageResource(R.drawable.ic_music_note_black_24dp)
        }
        if(currentMedia.type == "video")
        {
            holder.mediaType.setImageResource(R.drawable.ic_videocam_black_24dp)
        }

      //  holder.mediaplay.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp)



        holder.relativeLayout.setOnClickListener{
            listener?.onItemClick(ArrayList(medias),position)
        }
      //  holder.textUrl.text =  currentPhoto.url


    }
    fun setMedia(media : List<Media>)
    {
        this.medias = media
        notifyDataSetChanged()
    }



    interface OnMediaItemClickListener{
        fun onItemClick(listMedia : ArrayList<Media>,position : Int)
    }

}