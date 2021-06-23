package com.example.firstdemo.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.firstdemo.Transform
import com.example.firstdemo.data.remote.Daily
import com.example.firstdemo.glide.GlideApp
import com.example.firstdemo.shared_pereference.SettingsShared

@BindingAdapter("dailyCurrentDateTime")
fun TextView.setDailyCurrentDateTime(daily : Daily?) {
    daily?.let {
        text = Transform.convertLongToTime(daily.dt)
    }
}

@BindingAdapter("dailyWeatherDescription")
fun TextView.setDailyWeatherDescription(daily : Daily?) {
    daily?.let {
        text = daily.weather[0].description
    }
}
@BindingAdapter("dailyTemperature")
fun TextView.setDailyTemperatureText(daily: Daily?) {
    val temperature = context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()
    val unit: String = when (temperature) {
        "Celsius" -> "metric"
        "Fahrenheit" -> "imperial"
        else -> ""
    }
    daily?.let {
        text = daily.temp.day.toString() + Transform.transformTemperatureUnit(unit)
    }
}


@BindingAdapter("dailyHumidity")
fun TextView.setDailyHumidity(daily: Daily?) {
    val temperature = context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()
    val unit: String = when (temperature) {
        "Celsius" -> "metric"
        "Fahrenheit" -> "imperial"
        else -> ""
    }
    daily?.let {
        text = daily.humidity.toString() + "%"
    }
}

@BindingAdapter("dailyPressure")
fun TextView.setDailyPressure(daily : Daily?) {
    daily?.let {
        text = daily.pressure.toString() + " hPa"
    }
}

@BindingAdapter("dailyWindSpeed")
fun TextView.setDailyWind(daily: Daily?) {
    val temperature = context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()
    val unit: String = when (temperature) {
        "Celsius" -> "metric"
        "Fahrenheit" -> "imperial"
        else -> ""
    }
    daily?.let {
        text = daily.wind_speed.toString() + Transform.transformWindUnit(unit)
    }
}

@BindingAdapter("dailyCloud")
fun TextView.setDailyClouds(daily : Daily?) {
    daily?.let {
        text = daily.clouds.toString() + " %"
    }
}

@BindingAdapter("icon")
fun ImageView.setIcon(daily: Daily?) {
    daily?.let {dialy ->
        context?.let {
            GlideApp.with(it)
                .load("http://openweathermap.org/img/wn/${daily?.weather?.get(0)?.icon}@2x.png").into(this)
        }
    }

}



