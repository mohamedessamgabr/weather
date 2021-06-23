package com.example.firstdemo.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Place::class], version = 1, exportSchema = false)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun getDaoInstance(): PlaceDao

    companion object {
        var instance: PlaceDatabase? = null
        fun getDatabase(context: Context): PlaceDatabase {
            instance ?: synchronized(this) {
                var roomInstance =Room.databaseBuilder(context.applicationContext, PlaceDatabase::class.java, "PlaceDB").build()
                instance =roomInstance
            }
            return instance!!
        }
    }
}


