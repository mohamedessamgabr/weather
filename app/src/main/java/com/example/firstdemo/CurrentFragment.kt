package com.example.firstdemo

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firstdemo.adapter.DailyWeatherListAdapter
import com.example.firstdemo.adapter.HourlyWeatherListAdapter
import com.example.firstdemo.databinding.FragmentCurrentBinding
import com.example.firstdemo.glide.GlideApp
import com.example.firstdemo.location.LocationLiveData
import com.example.firstdemo.location.LocationViewModel
import com.example.firstdemo.shared_pereference.SettingsShared
import com.example.firstdemo.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*


class CurrentFragment : Fragment() {
    private lateinit var useMobileLocation: String
    private lateinit var onlineMode: String
    private lateinit var offlineMode: String
    private lateinit var progressDialog: ProgressDialog
    private lateinit var language: String
    private var temperature: String = "Celsius"
    private var city: String = ""
    private lateinit var myView: View


    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var binding: FragmentCurrentBinding


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onBackPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_current,
            container,
            false
        )








        binding.lifecycleOwner = this
        progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Loading")
        progressDialog.show()
        if (container != null) {
            myView = container
        }

        temperature = context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()
        language = context?.let { SettingsShared.readLanguage(it).toString() }.toString()



        binding.settingsToolbar.menu.getItem(0).setOnMenuItemClickListener {
            if (container != null) {
                Navigation.findNavController(container)
                    .navigate(R.id.action_currentFragment_to_settingsFragment)
            }
            true
        }

        binding.settingsToolbar.menu.getItem(1).setOnMenuItemClickListener {
            if (container != null) {
                Navigation.findNavController(container)
                    .navigate(R.id.action_currentFragment_to_favouritesFragment)
            }
            true
        }


        binding.hourlyRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.dailyRecycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        weatherViewModel =
            ViewModelProvider(this).get(WeatherViewModel::class.java)





        initViews()


        /*val periodicWorkRequest = PeriodicWorkRequest.Builder(AlertWorker::class.java, 15, TimeUnit.MINUTES)
            .setConstraints(Constraints.NONE)
            .build()
        val workManager = context?.let { WorkManager.getInstance(it) }
        workManager?.enqueue(periodicWorkRequest)
*/

//        val workManager = context?.let { WorkManager.getInstance(it) }
//        workManager?.enqueue(OneTimeWorkRequest.from(AlertWorker::class.java))


        return binding.root
    }


    private fun onBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val alertDialog = AlertDialog.Builder(activity)
                alertDialog.setTitle("Are you want to exit application ?")
                alertDialog.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
                    activity?.finish()
                }
                alertDialog.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                alertDialog.show()

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback)
    }


    private fun initViews() {

        var unit: String = when (temperature) {
            "Celsius" -> "metric"
            "Fahrenheit" -> "imperial"
            else -> ""
        }

        val locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        locationViewModel.locationLiveData.observe(viewLifecycleOwner, { cl ->
            if (cl != null) {
                weatherViewModel.getWeather(
                    cl.lon,
                    cl.lat,
                    "minutely",
                    unit,
                    language
                ).observe(
                    viewLifecycleOwner,
                    {

                        //binding.progress.visibility=View.GONE
                        if (it != null) {
                            progressDialog.hide()
                            binding.current = it.current
                            binding.executePendingBindings()
                            binding.cityText.text = cl.city
                            binding.weatherIcon.visibility = View.VISIBLE


                            val hourlyAdapter = HourlyWeatherListAdapter()
                            binding.hourlyRecycler.adapter = hourlyAdapter

                            val dailyAdapter = DailyWeatherListAdapter(cl.city)
                            binding.dailyRecycler.adapter = dailyAdapter

                            dailyAdapter.submitList(it.daily)
                            hourlyAdapter.submitList(it.hourly)

                        }

                    })
            }
        })

    }
}