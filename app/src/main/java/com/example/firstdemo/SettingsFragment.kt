package com.example.firstdemo

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.firstdemo.databinding.FragmentSettingsBinding
import com.example.firstdemo.shared_pereference.SettingsShared
import java.util.*
import kotlin.system.exitProcess


class SettingsFragment : Fragment() {


    private var selectedLanguage = "English"
    private var selectedTemperature = "Kelvin"
    private lateinit var binding: FragmentSettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater,R.layout.fragment_settings, container, false)


        binding.windSpeedUnit.text = when(selectedTemperature) {
            "Kelvin" -> "meter/second"
            "Celsius" -> "meter/second"
            else -> "mile/hour"
        }
        binding.tempUnit.text = context?.let { SettingsShared.readTemperatureUnit(it).toString() }.toString()
        binding.LanguageNameText.text = context?.let { SettingsShared.readLanguage(it).toString() }.toString()

        binding.tempLayout.setOnClickListener {
            showTemperatureUnits()
        }
        binding.langLayout.setOnClickListener {
            showLanguage()
        }

        setSwitch()

        binding.switchLocation.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                context?.let { it1 -> SettingsShared.writeMobileLocation(it1, "on") }
                buttonView.isChecked = true
            } else {
                context?.let { it1 -> SettingsShared.writeMobileLocation(it1, "off") }
                buttonView.isChecked = false
            }
        }



        binding.settingsToolbar.setNavigationOnClickListener {
            if (container != null) {
                Navigation.findNavController(container).navigate(R.id.action_settingsFragment_to_currentFragment)
            }
        }

        return binding.root
    }


    private fun showTemperatureUnits() {
        val units = arrayOf("Kelvin", "Celsius", "Fahrenheit")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose temperature unit")
        builder.setSingleChoiceItems(units, 0) { dialog, which ->
            selectedTemperature = units[which]
            binding.tempUnit.text = selectedTemperature

            binding.windSpeedUnit.text = when(selectedTemperature) {
                "Kelvin" -> "meter/second"
                "Celsius" -> "meter/second"
                else -> "mile/hour"
            }

            context?.let { SettingsShared.writeTemperatureUnit(selectedTemperature, it) }
            dialog.dismiss()
        }
        builder.show()
    }




    private fun showLanguage() {
        val languages = arrayOf("ar", "en")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Language")
        builder.setSingleChoiceItems(languages, 0) { dialog, which ->
            selectedLanguage = languages[which]
            binding.LanguageNameText.text = selectedLanguage
            context?.let { SettingsShared.writeLanguage(selectedLanguage, it) }
            dialog.dismiss()
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)

        }
        builder.show()
    }

    private fun setSwitch() {
        binding.switchLocation.isChecked = context?.let { SettingsShared.readMobileLocation(it) }.toString() == "on"
    }


}