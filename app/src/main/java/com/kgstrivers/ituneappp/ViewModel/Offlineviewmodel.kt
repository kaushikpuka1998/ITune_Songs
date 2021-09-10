package com.kgstrivers.ituneappp.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kgstrivers.ituneappp.Models.Result
import com.kgstrivers.ituneappp.Models.results

class Offlineviewmodel:ViewModel() {

    lateinit var livedatalist: MutableLiveData<List<results>>

    init {
        livedatalist = MutableLiveData()
    }

    fun getliveDataObserver(): MutableLiveData<List<results>> {
        return livedatalist
    }

    fun makeAPIcalloffline(items: List<results>)
    {

        if(items == null)
        {
            livedatalist.postValue(null)
        }
        else {
            livedatalist.postValue(items)
        }


    }
}