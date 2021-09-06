package com.kgstrivers.ituneappp.Retrofit

import retrofit2.Response

import com.kgstrivers.ituneappp.Models.Result
import retrofit2.Call
import retrofit2.http.*


interface RetroInterface {

    @GET("search")
    @Headers("User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
    fun getArtistdetails(@Query("term") term:String): Call<Result>
}