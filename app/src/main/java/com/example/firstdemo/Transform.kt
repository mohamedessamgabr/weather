package com.example.firstdemo

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import java.text.SimpleDateFormat
import java.util.*

object Transform {

    private var temperatureUnit : String? = null
    private var windUnit : String? = null

    fun transformTemperatureUnit(unit : String) : String {
        temperatureUnit = when (unit){
            "metric" -> "C"
            "imperial" -> "F"
            else -> "K"
        }
        return temperatureUnit as String
    }


    fun transformWindUnit(unit : String) : String {
        windUnit = when (unit){
            "metric" -> "m/s"
            "imperial" -> "mile/h"
            else -> "m/s"
        }
        return windUnit as String
    }



    fun convertLongToTime(time: Long): String {
        return SimpleDateFormat("E dd MMM yyyy hh:mm a").format(Date(time * 1000))
    }
    /*fun setLocale(context : Context, code : String) {
        val resources: Resources = context.resources
        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            config.setLocale(Locale(code.toLowerCase()))
        } else {
            config.locale = Locale(code.toLowerCase())
        }
        resources.updateConfiguration(config, dm)

    }*/
}