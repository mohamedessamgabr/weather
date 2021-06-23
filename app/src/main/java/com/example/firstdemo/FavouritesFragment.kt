package com.example.firstdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstdemo.adapter.FavouriteListAdapter
import com.example.firstdemo.data.local.PlaceDatabase
import com.example.firstdemo.databinding.FragmentFavouritesBinding
import com.example.firstdemo.viewModel.PlaceViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class FavouritesFragment : Fragment() {

    private lateinit var adapter : FavouriteListAdapter
    private lateinit var viewModel : PlaceViewModel
    private lateinit var binding: FragmentFavouritesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_favourites, container, false)

        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        binding.favouritesRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewModel.getPlaces(requireContext()).observe(viewLifecycleOwner, {
            adapter = context?.let { it1 -> FavouriteListAdapter(it1,it, container as View) }!!
            binding.favouritesRecycler.adapter = adapter

        })

        binding.favouritesToolbar.setNavigationOnClickListener {
            if (container != null) {
                Navigation.findNavController(container).navigate(FavouritesFragmentDirections.actionFavouritesFragmentToCurrentFragment())
            }
        }

        binding.addPlaceFab.setOnClickListener{
            if (container != null) {
                Navigation.findNavController(container).navigate(FavouritesFragmentDirections.actionFavouritesFragmentToMapsFragment())
            }
        }



        ItemTouchHelper (object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean  = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                var x = adapter.places[viewHolder.adapterPosition]
                adapter.places.removeAt(viewHolder.adapterPosition)
                adapter.notifyDataSetChanged()
                GlobalScope.launch {
                    val db = context?.let { PlaceDatabase.getDatabase(it) }
                    db?.getDaoInstance()?.deletePlace(x)
                }

            }

        }).attachToRecyclerView(binding.favouritesRecycler)


        return binding.root
    }


}