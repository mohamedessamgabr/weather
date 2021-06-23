package com.example.firstdemo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.firstdemo.data.Converters
import com.example.firstdemo.data.remote.WeatherResponse

@Database(entities = [WeatherResponse::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherDatabase : RoomDatabase(){

    abstract fun getWeatherDaoInstance() : WeatherDao

    companion object {
        var instance : WeatherDatabase? = null
        fun getWeatherDatabase(context : Context) : WeatherDatabase {
            instance?: synchronized(this){
                val roomInstance = Room.databaseBuilder(context.applicationContext, WeatherDatabase::class.java, "weatherDB")
                    .build()
                instance =roomInstance
            }
            return instance!!
        }
    }
}