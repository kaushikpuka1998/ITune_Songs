package com.kgstrivers.ituneappp.DB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "details",)
data class RoomEntity (

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name ="id") val id:Int,
    @ColumnInfo(name = "artistName") val artistName:String?,
    @ColumnInfo(name = "trackName") val trackName:String?,
    @ColumnInfo(name = "previewUrl") val previewUrl:String?,
    @ColumnInfo(name = "artworkUrl100") val artworkUrl100:String?,
    @ColumnInfo(name = "trackPrice") val trackPrice:String?,
    @ColumnInfo(name = "primaryGenreName") val primaryGenreName:String?,
    @ColumnInfo(name = "value") val value:String,
    )