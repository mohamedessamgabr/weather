package com.example.firstdemo.data

import androidx.room.TypeConverter
import com.example.firstdemo.data.remote.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromCurrent(current: Current): String {
        return Gson().toJson(current)
    }

    @TypeConverter
    fun toCurrent(string: String): Current {
        return Gson().fromJson(string, Current::class.java)
    }

    @TypeConverter
    fun fromHourly(hourly: List<Hourly>): String {
        return Gson().toJson(hourly)
    }

    @TypeConverter
    fun toHourly(string: String): List<Hourly> {
        val type = object : TypeToken<List<Hourly>>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun fromDaily(daily: List<Daily>): String {
        return Gson().toJson(daily)
    }

    @TypeConverter
    fun toDaily(string: String): List<Daily> {
        val type = object : TypeToken<List<Daily>>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun fromAlerts(alerts: List<Alerts>?): String? {
        return Gson().toJson(alerts)
    }

    @TypeConverter
    fun toAlerts(string: String?): List<Alerts>? {
        val type = object : TypeToken<List<Alerts>>() {}.type
        return Gson().fromJson(string, type)
    }

    @TypeConverter
    fun fromMinutely(minutely: List<Minutely>?): String? {
        return Gson().toJson(minutely)
    }

    @TypeConverter
    fun toMinutely(string: String?): List<Minutely>? {
        val type = object : TypeToken<List<Minutely>>() {}.type
        return Gson().fromJson(string, type)
    }




}