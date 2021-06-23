package com.example.firstdemo.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.firstdemo.R
import com.example.firstdemo.data.local.Place

class FavouriteListAdapter (var context: Context, _places: MutableList<Place>, var view : View): RecyclerView.Adapter<FavouriteListAdapter.MyViewHolder>() {

    var places = _places
    set(value) {
        field = value
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.favourite_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var item = places[position]
        holder.cityText.text = item.city

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("latitude", item.latitude)
            bundle.putString("longitude", item.longitude)
            bundle.putString("city", item.city)
            Navigation.findNavController(view)
                .navigate(R.id.action_favouritesFragment_to_favouriteItemFragment, bundle)
        }

    }

    override fun getItemCount(): Int {
        return places.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val cityText : TextView = itemView.findViewById(R.id.city_text)
    }
}