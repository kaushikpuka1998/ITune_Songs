package com.kgstrivers.ituneappp

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi

import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager

import androidx.recyclerview.widget.RecyclerView

import com.kgstrivers.ituneappp.DB.RoomDB
import com.kgstrivers.ituneappp.DB.RoomEntity

import com.kgstrivers.ituneappp.Models.results
import com.kgstrivers.ituneappp.Recyclerviewadapter.RecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.kgstrivers.ituneappp.ViewModel.Resultviewmodel

class MainActivity : AppCompatActivity() {
    var bb=ArrayList<results>()
    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initrecycleview()

            submitbutton.setOnClickListener {
            var g = artistnamesearch.text.toString()

                if(g.isEmpty()){


                    Toast.makeText(this,"Empty Author",Toast.LENGTH_SHORT).show()
                }else
                {
                    if(isOnline(this))
                    {

                        initviewmodel(g)



                    }else
                    {
                        val gl:ArrayList<results> = getvaluefromroom(g)

                        recyclerviewAdapter.listdata(gl)
                        recyclerviewAdapter.notifyDataSetChanged()
                        artistnamesearch.setText("")
                    }
                }



        }

    }


    lateinit var recyclerviewAdapter:RecyclerviewAdapter

    private fun initrecycleview()
    {
        recycleview.apply { layoutManager= GridLayoutManager(this@MainActivity,2,
            RecyclerView.VERTICAL,false)

            recyclerviewAdapter = RecyclerviewAdapter()
            adapter = recyclerviewAdapter

        }


    }
    private  fun initviewmodel(artist:String)
    {
        val viewmodel = ViewModelProvider(this).get(Resultviewmodel::class.java)

        viewmodel.getliveDataObserver().observe(this,{

            if(it!=null)
            {


                bb = it.results as ArrayList<results>



                recyclerviewAdapter.listdata(bb)

                postdatatoroom(bb,artist)


                recyclerviewAdapter.notifyDataSetChanged()

                System.out.println("Data Came $it")

            }
            else{
                Toast.makeText(this,"Error in getting list", Toast.LENGTH_LONG).show()
            }


        })

        viewmodel.makeAPIcall(artist)
    }

    private fun postdatatoroom(items:ArrayList<results>,value:String)
    {
        val roomdao = RoomDB.getAppDatabase(applicationContext)?.roomdao()

        //roomdao?.delete(value)
        for(item in items)
        {



            roomdao?.insertSong(RoomEntity(
                roomdao.size()+1,item.artistName,item.trackName,item.previewUrl,item.artworkUrl100,item.trackPrice.toString(),item.primaryGenreName,value))
        }

        //Toast.makeText(applicationContext,"Room Upload Successfull",Toast.LENGTH_SHORT).show()
    }

    private fun getvaluefromroom(value:String):ArrayList<results>
    {
        var ret = ArrayList<results>()

        val roomdao = RoomDB.getAppDatabase(applicationContext)?.roomdao()

        var ent=roomdao?.loadSong(value)

        if (ent != null) {

            //Toast.makeText(applicationContext,"Room Data is Not NUll ${ent.size}",Toast.LENGTH_SHORT).show()
            for(item in ent)
            {

                var rooment = RoomEntity(item.id,item.artistName,item.trackName,item.previewUrl,item.artworkUrl100,item.trackPrice,item.primaryGenreName,item.value)

                //Toast.makeText(applicationContext,rooment.artistName,Toast.LENGTH_SHORT).show()
                rooment.trackName?.let {
                    rooment.artistName?.let { it1 ->
                        rooment.artworkUrl100?.let { it2 ->
                            item.primaryGenreName?.let { it3 ->
                                rooment.trackPrice?.let { it4 ->
                                    results(
                                        it1,
                                        it,"", it2, it4.toDouble(), it3
                                    )
                                }
                            }
                        }
                    }
                }?.let { ret.add(it) }
            }
        }
        else
        {
            Toast.makeText(applicationContext,"Room Data is NUll",Toast.LENGTH_SHORT).show()
        }

        return ret
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