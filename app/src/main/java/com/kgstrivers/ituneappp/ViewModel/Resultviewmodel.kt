package com.kgstrivers.ituneappp.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.kgstrivers.ituneappp.Models.Result
import com.kgstrivers.ituneappp.Models.results
import com.kgstrivers.ituneappp.Retrofit.RetroInterface
import com.kgstrivers.ituneappp.Retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Resultviewmodel:ViewModel() {

    lateinit var livedatalist:MutableLiveData<Result>

    init {
        livedatalist = MutableLiveData()
    }

    fun getliveDataObserver():MutableLiveData<Result>{
        return livedatalist
    }

    fun makeAPIcall(item:String)
    {
        val retrofitInstance = RetrofitInstance.getRetrofit()

        val retroservice = retrofitInstance.create(RetroInterface::class.java)

        val call = retroservice.getArtistdetails(item)
        call.enqueue(object : Callback<Result> {
            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {

                if(response.isSuccessful)
                {
                    //Log.d("Checkkkkkkkkkkk Body:",response.body().toString())
                    livedatalist.postValue(response.body())
                }
                else
                {
                    Log.d("ettttttttttttttt:",response.errorBody().toString())
                }

                //Toast.makeText(this@CatagoryViewmodel,"PRoblem Found to Fetch",Toast.LENGTH_LONG).show()


            }

            override fun onFailure(call: Call<Result>, t: Throwable) {


                Log.d("ERRRRRRRRRRRRRERRERER:",t.message.toString())
                livedatalist.postValue(null)


            }


        })


    }


    fun makeAPIcalloffline(items: Result)
    {
        livedatalist.postValue(items)


    }
}

