package com.kgstrivers.ituneappp.Models

data class Result(val results:List<results>)

data class results(
                //val wrapperType:String,
                //val kind:String,
                //val artistid:Int,
                //val trackId:Int,
                val artistName:String,
                //val collectionName:String,
                val trackName:String,
                //val collectionCensoredName :String,
                //val trackCensoredName:String,
                //val collectionArtistId:Int,
                //val collectionArtistName:String,
                //val collectionArtistViewUrl:String,
                //val artistViewUrl:String,
                //val collectionViewUrl:String,
                val previewUrl:String,
                //val artworkUrl30:String,
                //val artworkUrl60:String,
                val artworkUrl100:String,
                //val collectionPrice:Double,
                val trackPrice:Double,
                //val releaseDate:String,
                //val collectionExplicitness:String,
                //val trackExplicitness:String,
                //val discCount:Int,
                //val discNumber:String,
                //val trackCount:Int,
                //val trackNumber:Int,
                //val trackTimeMillis:Int,
                //val country:String,
                //val currency:String,
                val primaryGenreName:String,)
                //val isStreamable:Boolean)