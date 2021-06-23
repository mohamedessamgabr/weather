package com.example.firstdemo.data.remote

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL =
    "https://api.openweathermap.org/"
private const val KEY = "bebabbecab8ba5afe367ccb7da6056c1"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()


interface ApiService {
    @GET("data/2.5/onecall")
    fun getWeather(@Query("lat") lat: String, @Query("lon") lon: String, @Query("exclude") exclude: String,
                   @Query("units") units : String = "metric", @Query("lang") language : String = "en",
                   @Query("APPID") app_id: String = "bebabbecab8ba5afe367ccb7da6056c1",) : Call<WeatherResponse>
}

object WeatherApi {
    val retrofitService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}