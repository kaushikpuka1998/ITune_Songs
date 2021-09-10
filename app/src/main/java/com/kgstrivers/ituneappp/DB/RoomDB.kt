package com.kgstrivers.ituneappp.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [RoomEntity::class],version=1)
abstract class RoomDB : RoomDatabase(){

    abstract fun roomdao():RoomDAO?

    companion object{


        private var INSTANCE:RoomDB?=null


        fun getAppDatabase(context:Context): RoomDB? {


            if(INSTANCE==null)
            {
                INSTANCE = Room.databaseBuilder<RoomDB>(
                    context.applicationContext,RoomDB::class.java,"Room Database"
                ).allowMainThreadQueries().build()
            }


            return INSTANCE
        }
    }
}