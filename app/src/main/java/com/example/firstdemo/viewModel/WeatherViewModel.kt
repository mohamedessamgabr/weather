package com.example.firstdemo.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.firstdemo.data.remote.WeatherResponse
import com.example.firstdemo.repository.Repository

class WeatherViewModel(application: Application): AndroidViewModel(application) {

    var weatherResponseLiveData = MutableLiveData<WeatherResponse>()
    private var repository = Repository()


    fun getWeather(latitude: String, longitude: String, exclude: String, unit: String, language: String) : MutableLiveData<WeatherResponse> {
        repository.getDataFromNetwork(latitude, longitude, exclude, unit, language, getApplication())

        weatherResponseLiveData = repository.weatherResponseLiveData

        return weatherResponseLiveData
    }

}