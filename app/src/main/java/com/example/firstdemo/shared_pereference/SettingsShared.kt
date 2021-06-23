package com.example.firstdemo.shared_pereference

import android.content.Context
import android.content.SharedPreferences

object SettingsShared {

    // temperature
    private const val TEMP_SHARED = "TemperatureShared"
    private const val TEMPERATURE_TAG = "temperatureUnit"
    private var writeTemperatureUnit : SharedPreferences? = null
    private var readTemperatureUnit : SharedPreferences? = null

    fun writeTemperatureUnit(unit : String, context: Context){
        writeTemperatureUnit = context?.getSharedPreferences( TEMP_SHARED,Context.MODE_PRIVATE) ?: return
        with(writeTemperatureUnit!!.edit()) {
            putString(TEMPERATURE_TAG, unit)
            apply()
        }
    }

    fun readTemperatureUnit(context: Context) : String? {
        readTemperatureUnit = context?.getSharedPreferences(TEMP_SHARED, Context.MODE_PRIVATE)

        return readTemperatureUnit!!.getString(TEMPERATURE_TAG, "Celsius")

    }


    // language
    private const val language_shared = "langShared"
    private const val language_TAG = "LangUnit"
    private var writeLanguage : SharedPreferences? = null
    private var readLanguage : SharedPreferences? = null

    fun writeLanguage(unit : String, context: Context) {
        writeLanguage = context?.getSharedPreferences(language_shared, Context.MODE_PRIVATE) ?: return
        with(writeLanguage!!.edit()){
            putString(language_TAG, unit)
            apply()
        }
    }

    fun readLanguage(context: Context) : String? {
        readLanguage = context?.getSharedPreferences(language_shared, Context.MODE_PRIVATE)
        return  readLanguage!!.getString(language_TAG, "en")
    }


    // use mobile location
    private const val MOBILE_LOCATION_SHARED = "mobile_location_shared"
    private const val MOBILE_LOCATION_TAG = "mobile_location_tag"
    private lateinit var writeMobileLocation : SharedPreferences
    private lateinit var readMobileLocation : SharedPreferences

    fun writeMobileLocation(context: Context, status : String) {
        writeMobileLocation = context.getSharedPreferences(MOBILE_LOCATION_SHARED, Context.MODE_PRIVATE)
            ?: return
        with(writeMobileLocation.edit()){
            putString(MOBILE_LOCATION_TAG, status)
            apply()
        }
    }

    fun readMobileLocation(context: Context) : String? {
        readMobileLocation = context?.getSharedPreferences(MOBILE_LOCATION_SHARED, Context.MODE_PRIVATE)
        return readMobileLocation!!.getString(MOBILE_LOCATION_TAG, "on")
    }


    // online or not
    private lateinit var writeOnlineMode : SharedPreferences
    private lateinit var readOnlineMode : SharedPreferences

    fun writeOnlineMode(context: Context, status: String) {
        writeOnlineMode = context?.getSharedPreferences("online_shared", Context.MODE_PRIVATE) ?: return
        with(writeOnlineMode!!.edit()) {
            putString("online_tag", status)
            apply()
        }
    }

    fun readOnlineMode(context: Context) : String? {
        readOnlineMode = context?.getSharedPreferences("online_shared", Context.MODE_PRIVATE)
        return readOnlineMode!!.getString("online_tag", "on")
    }



    private lateinit var writeOfflineMode : SharedPreferences
    private lateinit var readOfflineMode : SharedPreferences

    fun writeOfflineMode(context: Context, status: String) {
        writeOfflineMode = context?.getSharedPreferences("offline_shared", Context.MODE_PRIVATE) ?: return
        with(writeOfflineMode!!.edit()) {
            putString("offline_tag", status)
            apply()
        }
    }

    fun readOfflineMode(context: Context) : String? {
        readOfflineMode = context?.getSharedPreferences("offline_shared", Context.MODE_PRIVATE)
        return readOfflineMode!!.getString("offline_tag", "off")
    }

}