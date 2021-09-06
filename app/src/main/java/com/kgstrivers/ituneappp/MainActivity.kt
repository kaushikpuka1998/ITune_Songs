package com.kgstrivers.ituneappp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.kgstrivers.ituneappp.Models.Result
import com.kgstrivers.ituneappp.ViewModel.Resultviewmodel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initviewmodel()

    }

    private  fun initviewmodel()
    {
        val viewmodel = ViewModelProvider(this).get(Resultviewmodel::class.java)

        viewmodel.getliveDataObserver().observe(this,{

            if(it!=null)
            {
                /*recyclerviewAdapter.listdata(it as ArrayList<Catagorymodel>)

                recyclerviewAdapter.notifyDataSetChanged()*/

                System.out.println("Data Came")

            }
            else{
                Toast.makeText(this,"Error in getting list", Toast.LENGTH_LONG).show()
            }


        })

        viewmodel.makeAPIcall("anupam")
    }
}