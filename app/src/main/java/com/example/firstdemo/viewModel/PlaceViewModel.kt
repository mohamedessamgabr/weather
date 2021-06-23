package com.example.firstdemo.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.firstdemo.data.local.Place
import com.example.firstdemo.repository.Repository

class PlaceViewModel : ViewModel() {

    private lateinit var _placesLiveData : LiveData<MutableList<Place>>

    private val repository = Repository()

    fun getPlaces(context: Context) : LiveData<MutableList<Place>> {

        _placesLiveData = repository.getPlacesFromDatabase(context)
        return _placesLiveData

    }
}