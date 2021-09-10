package com.kgstrivers.ituneappp.DB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
abstract interface RoomDAO {




    @Query("SELECT * FROM details ORDER BY  value = :filterValues OR artistName =:filterValues DESC")
    fun loadSong(filterValues:String): Array<RoomEntity>?


    @Insert()
    fun insertSong(value:RoomEntity)

    /*@Query("DELETE FROM details WHERE value LIKE :v ")
    fun delete(v :String)*/


    @Query("SELECT max(id) FROM details ")
    fun size():Int


}