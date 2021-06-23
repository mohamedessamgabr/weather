package com.example.firstdemo.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Build.VERSION_CODES.O
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.firstdemo.R
import com.example.firstdemo.data.remote.Alerts
import com.example.firstdemo.data.remote.ApiService
import com.example.firstdemo.data.remote.WeatherApi
import com.example.firstdemo.data.remote.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception
import javax.security.auth.callback.Callback

fun makeNotification(message: String, context: Context) {

    // Make a channel if necessary
    if (Build.VERSION.SDK_INT >= O) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = VERBOSE_NOTIFICATION_CHANNEL_NAME
        val description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description

        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel)


        // Create the notification
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(LongArray(0))

        // Show the notification
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

}


@WorkerThread
fun getAlerts(
    latitude: String,
    longitude: String,
    exclude: String,
    units: String,
    language: String,
    context: Context
) {

    var result = "No dangerous conditions"
    WeatherApi.retrofitService.getWeather(latitude, longitude, "current,minutely,hourly,daily", units, language)
        .enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {

                val list = response.body()?.alerts
                if (list != null) {
                    for (i in list){
                        if (!i.description.isNullOrEmpty()){
                            result = i.description
                        }
                    }
                }

                makeNotification(result, context)
//                result = if (response.body()?.alerts != null) {
//                    response.body()?.alerts!![0].description
//                } else {
//                    "No dangerous conditions"
//                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.i("Essam", t.message.toString())
                makeNotification("No Network", context)
            }

        })




}