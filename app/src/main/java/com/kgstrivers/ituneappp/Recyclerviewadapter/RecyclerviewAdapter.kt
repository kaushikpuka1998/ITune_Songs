package com.kgstrivers.ituneappp.Recyclerviewadapter

import android.graphics.Color
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kgstrivers.ituneappp.DB.RoomDAO
import com.kgstrivers.ituneappp.Models.Result
import com.kgstrivers.ituneappp.Models.results
import com.kgstrivers.ituneappp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.singleitem.view.*
import java.lang.Exception

class RecyclerviewAdapter: RecyclerView.Adapter<RecyclerviewAdapter.MyViewHolder>() {


    var list = ArrayList<results>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var artistname = view.artistname
        var trackname = view.trackname
        var trackprice = view.price
        var genre = view.genre
        var image = view.albumpic
        var playbutton = view.play





        fun bind(data: results) {
            artistname.setText(data.artistName)
            trackname.setText(data.trackName)
            trackprice.setText(data.trackPrice.toString() + " USD")
            genre.setText(data.primaryGenreName)
            Picasso.with(itemView.context).load(data.artworkUrl100).fit().centerCrop().into(image)
            playbutton.setImageResource(R.drawable.ic_play)
            var mediaaplayer = MediaPlayer()
            playbutton.setOnClickListener {


                if (mediaaplayer.isPlaying) {

                    mediaaplayer.pause()
                    playbutton.setImageResource(R.drawable.ic_play)
                } else {


                    mediaaplayer.start()
                    playbutton.setImageResource(R.drawable.ic_baseline_motion_photos_paused_24)
                }

                mediaaplayer.setOnCompletionListener {

                    playbutton.setImageResource(R.drawable.ic_play)
                }
            }
            try {
                mediaaplayer.setDataSource(data.previewUrl)
                mediaaplayer.prepare()
            } catch (e: Exception) {
                //Toast.makeText(itemView.context, "Error $e", Toast.LENGTH_LONG).show()
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflateview =
            LayoutInflater.from(parent.context).inflate(R.layout.singleitem, parent, false)
        return MyViewHolder(inflateview)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list.get(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun listdata(item: ArrayList<results>) {


        this.list = item
    }


}