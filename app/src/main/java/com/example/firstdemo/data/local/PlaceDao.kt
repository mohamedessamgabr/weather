package com.example.firstdemo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place_table")
    fun getAllPlaces() : LiveData<MutableList<Place>>

    @Insert
    fun addPlace(place: Place)

    @Delete
    fun deletePlace(place: Place)
}