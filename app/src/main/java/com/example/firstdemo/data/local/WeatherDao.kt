package com.example.firstdemo.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.firstdemo.data.remote.WeatherResponse

@Dao
interface WeatherDao {

    @Insert
    suspend fun addWeather(weatherResponse: WeatherResponse)

    @Query("DELETE From weather_table")
    suspend fun deleteWeather()

   /* @Query("SELECT * FROM weather_table WHERE lat = :latitude AND lon = :longitude")
    fun get(latitude : String, longitude : String) : LiveData<List<WeatherResponse>>*/

    @Query("SELECT * FROM weather_table")
    fun getItem() : LiveData<WeatherResponse>
}