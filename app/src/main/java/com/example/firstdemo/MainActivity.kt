package com.example.firstdemo

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.firstdemo.shared_pereference.SettingsShared
import java.util.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private lateinit var lang : String

    /*private var permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private val requestCode = 0*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      /*  if (!checkPermission()) {
            ActivityCompat.requestPermissions(this, permissions, requestCode)
        }*/




    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        lang = SettingsShared.readLanguage(applicationContext).toString()
        setAppLocale(lang)

    }

    private fun setAppLocale(localeCode: String) {
        val resources: Resources = resources
        val dm: DisplayMetrics = resources.displayMetrics
        val config: Configuration = resources.configuration
        config.setLocale(Locale(localeCode.toLowerCase()))
        resources.updateConfiguration(config, dm)
    }


   /* override fun onBackPressed() {
        super.onBackPressed()
        val alertDialog = AlertDialog.Builder(ContextThemeWrapper(applicationContext, R.style.myDialog))
        alertDialog.setTitle("Are you want to exit application ?")
        alertDialog.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
            finish()
        }
        alertDialog.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        alertDialog.show()
    }*/

    /*private fun checkPermission() : Boolean {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true
        }
        return false
    }*/


}