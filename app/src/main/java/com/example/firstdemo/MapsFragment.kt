package com.example.firstdemo

import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.navigation.Navigation
import com.example.firstdemo.data.local.Place
import com.example.firstdemo.data.local.PlaceDatabase
import com.example.firstdemo.shared_pereference.SettingsShared

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class MapsFragment : Fragment() {


    private var language = "ar"
    lateinit var myview: View
    private var latitude = 30.2198
    private var longitude = 31.3639
    private val zoomLevel = 15f
    private var home = LatLng(latitude, longitude)
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        googleMap.addMarker(MarkerOptions().position(home).title("Marker at Home"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, zoomLevel))
        setMapClick(googleMap)
        save(googleMap)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (container != null) {
            myview = container
        }
        val view =  inflater.inflate(R.layout.fragment_maps, container, false)

        language = context?.let { SettingsShared.readLanguage(it) }.toString()
        view.findViewById< androidx.appcompat.widget.Toolbar>(R.id.map_toolbar).setNavigationOnClickListener {
            if (container != null) {
                Navigation.findNavController(container).navigate(R.id.action_mapsFragment_to_favouritesFragment)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun save(map: GoogleMap) {
        map.setOnMapLongClickListener {
            latitude = it.latitude
            longitude = it.longitude
            if (insertToDatabase()) {
                Navigation.findNavController(myview)
                    .navigate(R.id.action_mapsFragment_to_favouritesFragment)

            }
        }



    }

    private fun setMapClick(map: GoogleMap) {
        map.setOnMapClickListener { latLng ->
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
            )

        }
    }

    private fun getCity(): String? {
        val geoCoder = Geocoder(context, Locale(language))
        val address = geoCoder.getFromLocation(latitude, longitude, 1)
        if (address.size > 0) {
            return address[0].getAddressLine(0)
        }
        return null
    }

    private fun insertToDatabase(): Boolean {
        if (getCity() != null) {
            GlobalScope.launch {
                val db = PlaceDatabase.getDatabase(requireContext())
                val place = Place()
                place.latitude = latitude.toString()
                place.longitude = longitude.toString()
                place.city = getCity() ?: ""
                db.getDaoInstance().addPlace(place)
            }
            return true
        }
        return false
    }
}