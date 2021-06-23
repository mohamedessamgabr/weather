package com.example.firstdemo

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.firstdemo.data.remote.Current
import com.example.firstdemo.glide.GlideApp
import com.example.firstdemo.shared_pereference.SettingsShared

@BindingAdapter("currentDateTime")
fun TextView.setDailyCurrentDateTime(current: Current?) {
    current?.let {
        text = Transform.convertLongToTime(current.dt)
    }
}

@BindingAdapter("currentWeatherDescription")
fun TextView.setDailyWeatherDescription(current: Current?) {
    current?.let {
        text = current.weather[0].description
    }
}
@BindingAdapter("currentTemperature")
fun TextView.setDailyTemperatureText(current: Current?) {
    val temperature = context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()
    val unit: String = when (temperature) {
        "Celsius" -> "metric"
        "Fahrenheit" -> "imperial"
        else -> ""
    }
    current?.let {
        text = current.temp.toString() + Transform.transformTemperatureUnit(unit)
    }
}


@BindingAdapter("currentHumidity")
fun TextView.setDailyHumidity(current: Current?) {
    val unit: String = when (context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()) {
        "Celsius" -> "metric"
        "Fahrenheit" -> "imperial"
        else -> ""
    }
    current?.let {
        text = current.humidity.toString() + "%"
    }
}

@BindingAdapter("currentPressure")
fun TextView.setDailyPressure(current: Current?) {
    current?.let {
        text = current.pressure.toString() + " hPa"
    }
}

@BindingAdapter("currentWindSpeed")
fun TextView.setDailyWind(current: Current?) {
    val unit: String = when (context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()) {
        "Celsius" -> "metric"
        "Fahrenheit" -> "imperial"
        else -> ""
    }
    current?.let {
        text = current.wind_speed.toString() + Transform.transformWindUnit(unit)
    }
}

@BindingAdapter("currentCloud")
fun TextView.setDailyClouds(current: Current?) {
    current?.let {
        text = current.clouds.toString() + " %"
    }
}

@BindingAdapter("currentIcon")
fun ImageView.setIcon(current: Current?) {
    current?.let {
        context?.let {
            GlideApp.with(it)
                .load("http://openweathermap.org/img/wn/${current.weather[0].icon}@2x.png").into(this)
        }
    }

}