package com.example.firstdemo.data.remote

import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Alerts (

    val sender_name : String,
    val event : String,
    val start : Int,
    val end : Int,
    val description : String
)

data class Weather (

    val id : Int,
    val main : String,
    val description : String,
    val icon : String
)

data class Temp (

    val day : Double,
    val min : Double,
    val max : Double,
    val night : Double,
    val eve : Double,
    val morn : Double
)

data class Minutely (

    val dt : Long,
    val precipitation : Int
)



@Entity(tableName = "weather_table")
data class WeatherResponse (

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val lat : Double,
    val lon : Double,
    val timezone : String,
    val timezone_offset : Int,
    val current : Current,
    val minutely : List<Minutely>?,
    val hourly : List<Hourly>,
    val daily : List<Daily>,
    val alerts : List<Alerts>?
)


data class Hourly (

    val dt : Long,
    val temp : Double,
    val feels_like : Double,
    val pressure : Int,
    val humidity : Int,
    val dew_point : Double,
    val uvi : Double,
    val clouds : Int,
    val visibility : Int,
    val wind_speed : Double,
    val wind_deg : Int,
    val weather : List<Weather>,
    val pop : Double
)

data class Feels_like (

    val day : Double,
    val night : Double,
    val eve : Double,
    val morn : Double
)

data class Daily (

    val dt : Long,
    val sunrise : Int,
    val sunset : Int,
    val temp : Temp,
    val feels_like : Feels_like,
    val pressure : Int,
    val humidity : Int,
    val dew_point : Double,
    val wind_speed : Double,
    val wind_deg : Int,
    val weather : List<Weather>,
    val clouds : Int,
    val pop : Double,
    val uvi : Double
)


data class Current (

    val dt : Long,
    val sunrise : Int,
    val sunset : Int,
    val temp : Double,
    val feels_like : Double,
    val pressure : Int,
    val humidity : Int,
    val dew_point : Double,
    val uvi : Double,
    val clouds : Int,
    val visibility : Int,
    val wind_speed : Double,
    val wind_deg : Int,
    val weather : List<Weather>
)