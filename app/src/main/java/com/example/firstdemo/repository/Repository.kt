package com.example.firstdemo.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firstdemo.data.local.Place
import com.example.firstdemo.data.local.PlaceDatabase
import com.example.firstdemo.data.local.WeatherDatabase
import com.example.firstdemo.data.remote.WeatherApi
import com.example.firstdemo.data.remote.WeatherResponse
import com.example.firstdemo.shared_pereference.SettingsShared
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Response

class Repository {

    var weatherResponseLiveData = MutableLiveData<WeatherResponse>()


    //var placesLiveData = MutableLiveData<List<Place>>()

    fun getDataFromNetwork(
        lat: String,
        lon: String,
        exclude: String,
        unit: String,
        language: String,
        context: Context
    ) {


        val db = WeatherDatabase.getWeatherDatabase(context)


        WeatherApi.retrofitService.getWeather(lat, lon, exclude, unit, language).enqueue(object :
            retrofit2.Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                //Log.i("Essam", response.body()?.current?.temp.toString())
                /*GlobalScope.launch {
                    db.getWeatherDaoInstance().deleteAll()
                }*/
                SettingsShared.writeOnlineMode(context, "on")
                SettingsShared.writeOfflineMode(context, "off")
                weatherResponseLiveData.value = response.body()
                GlobalScope.launch {
                    withContext(Dispatchers.Main) {
                        if (response.body() != null) {
                            db.getWeatherDaoInstance().deleteWeather()
                            db.getWeatherDaoInstance().addWeather(response.body()!!)
                        }
                    }
                }

            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                SettingsShared.writeOnlineMode(context, "off")
                SettingsShared.writeOfflineMode(context, "on")
            }

        })

    }


    fun getPlacesFromDatabase(context: Context): LiveData<MutableList<Place>> {
        val db = PlaceDatabase.getDatabase(context)
        return db.getDaoInstance().getAllPlaces()
    }


}