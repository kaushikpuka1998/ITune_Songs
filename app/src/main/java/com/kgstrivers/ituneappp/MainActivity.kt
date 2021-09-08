package com.kgstrivers.ituneappp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kgstrivers.ituneappp.Models.Result
import com.kgstrivers.ituneappp.Models.results
import com.kgstrivers.ituneappp.Recyclerviewadapter.RecyclerviewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import com.kgstrivers.ituneappp.ViewModel.Resultviewmodel

class MainActivity : AppCompatActivity() {
    var bb=ArrayList<results>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initrecycleview()
        artistnamesearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

                initviewmodel(s.toString())


            }

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {




                initviewmodel(s.toString())
            }
        })
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

                recyclerviewAdapter.notifyDataSetChanged()

                System.out.println("Data Came $it")

            }
            else{
                Toast.makeText(this,"Error in getting list", Toast.LENGTH_LONG).show()
            }


        })

        viewmodel.makeAPIcall(artist)
    }


}