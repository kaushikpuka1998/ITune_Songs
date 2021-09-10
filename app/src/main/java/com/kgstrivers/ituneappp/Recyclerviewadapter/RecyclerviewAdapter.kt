package com.kgstrivers.ituneappp.Recyclerviewadapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.kgstrivers.ituneappp.DB.RoomDAO
import com.kgstrivers.ituneappp.MainActivity
import com.kgstrivers.ituneappp.Models.Result
import com.kgstrivers.ituneappp.Models.results
import com.kgstrivers.ituneappp.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
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

            playbutton.setOnClickListener {

                openDialog(itemView,data.trackName,data.artistName,data.artworkUrl100,data.previewUrl,data.primaryGenreName,playbutton)
            }



        }

        fun openDialog(view:View,dtrack:String,dartist:String,dpic:String,dsongurl:String,dgenre:String,imgbtn:ImageButton)
        {
            val dialog = Dialog(view.context)
            dialog.setContentView(R.layout.dialog_layout)


            val dimage = dialog.findViewById<CircleImageView>(R.id.dialogimage)
            val dtrackname =dialog.findViewById<TextView>(R.id.dialogsongname)
            val dsinger = dialog.findViewById<TextView>(R.id.dialogsingername)
            val ddgenre = dialog.findViewById<TextView>(R.id.dialoggenrename)
            var dbutton = dialog.findViewById<Button>(R.id.dexit)



            Picasso.with(itemView.context).load(dpic).fit().centerCrop().into(dimage)
            dtrackname.setText(dtrack)
            dsinger.setText(dartist)
            ddgenre.setText(dgenre)

            var mediaaplayer = MediaPlayer()
            if(isOnline(view.context)) {

                try {
                    mediaaplayer.setDataSource(String.format(dsongurl))
                    mediaaplayer.prepare()
                } catch (e: Exception) {
                    Toast.makeText(itemView.context, "Error $e", Toast.LENGTH_LONG).show()
                }

                mediaaplayer.start()
            }else
            {
                Toast.makeText(view.context,"Song Will Not be Audible without Internet Connection",Toast.LENGTH_LONG).show()
            }
            imgbtn.setImageResource(R.drawable.ic_baseline_motion_photos_paused_24)

            dialog.show()

            dialog.setCanceledOnTouchOutside(false)


            dbutton.setOnClickListener {

                dialog.hide()
                mediaaplayer.stop()
                mediaaplayer.release()
                imgbtn.setImageResource(R.drawable.ic_play)
            }




        }

        fun isOnline(context: Context?): Boolean {
            if (context == null) return false
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    when {
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                            return true
                        }
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                            return true
                        }
                    }
                }
            } else {
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                    return true
                }
            }
            return false
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

        //this.list.clear()
        this.list = item
    }





}