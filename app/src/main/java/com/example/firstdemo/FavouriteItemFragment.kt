package com.example.firstdemo

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstdemo.adapter.DailyWeatherListAdapter
import com.example.firstdemo.adapter.HourlyWeatherListAdapter
import com.example.firstdemo.databinding.FragmentFavouriteItemBinding
import com.example.firstdemo.shared_pereference.SettingsShared
import com.example.firstdemo.viewModel.WeatherViewModel


class FavouriteItemFragment : Fragment() {
    private var temperature: String = "Celsius"
    private var language = "en"
    private lateinit var viewModel: WeatherViewModel
    private var city: String = "Home"
    private lateinit var binding: FragmentFavouriteItemBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_favourite_item,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)
        language = context?.let { SettingsShared.readLanguage(it).toString() }.toString()

        binding.favouriteDailyRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.favouriteHourlyRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.favouriteItemToolbar.setNavigationOnClickListener {

            it.findNavController().navigate(R.id.action_favouriteItemFragment_to_favouritesFragment)

        }
        temperature = context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()

        val args = arguments
        initViews(args?.getString("latitude") as String, args?.getString("longitude") as String)
        city = args?.getString("city") as String

        binding.favouriteItemToolbar.title = city
        return binding.root
    }


    private fun initViews(lat: String, lon: String) {

        var unit: String = when (temperature) {
            "Celsius" -> "metric"
            "Fahrenheit" -> "imperial"
            else -> ""
        }
        viewModel.getWeather(lat, lon, "minutely", unit, language).observe(
            viewLifecycleOwner,
            {

                if (it != null) {
                    var city = getCity(lat.toDouble(), lon.toDouble())
                    binding.current = it.current
                    binding.favouriteCityText.text = city
                    binding.executePendingBindings()

                    val hourlyAdapter = HourlyWeatherListAdapter()
                    binding.favouriteHourlyRecycler.adapter = hourlyAdapter

                    val dailyAdapter = DailyWeatherListAdapter(city)
                    binding.favouriteDailyRecycler.adapter = dailyAdapter
                    dailyAdapter.submitList(it.daily)
                    hourlyAdapter.submitList(it.hourly)
                }


            })
    }
    private fun getCity(lat : Double, lon: Double) : String{
        val geoCoder = Geocoder(context)
        val address = geoCoder.getFromLocation(lat, lon, 1)
        return if (address.size > 0)
            address[0].locality
        else
            "city"
    }

}