package com.example.firstdemo.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_table")
class Place {
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
    @ColumnInfo(name = "latitude")
    var latitude : String = ""
    @ColumnInfo(name = "longitude")
    var longitude : String = ""
    @ColumnInfo(name = "address")
    var city : String = ""
}