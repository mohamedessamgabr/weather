package com.example.firstdemo.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.firstdemo.Transform
import com.example.firstdemo.data.remote.Hourly
import com.example.firstdemo.glide.GlideApp
import com.example.firstdemo.shared_pereference.SettingsShared
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("temp")
fun TextView.setTemp(hourly : Hourly?) {
    var unit : String = when (context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()){
        "Celsius" -> "metric"
        "Fahrenheit" -> "imperial"
        else -> ""
    }
    hourly?.let {
        text = hourly.temp.toString() + Transform.transformTemperatureUnit(unit)
    }
}

@BindingAdapter("hour")
fun TextView.setHour(hourly: Hourly?) {
    hourly?.let {
        text = SimpleDateFormat("hh:mm a").format(Date(hourly.dt * 1000))
    }
}

@BindingAdapter("icon")
fun ImageView.setIcon(hourly: Hourly?) {
    hourly?.let { hourly ->
        context?.let {
            GlideApp.with(it)
                .load("http://openweathermap.org/img/wn/${hourly.weather[0].icon}@2x.png").into(this)
        }

    }
}