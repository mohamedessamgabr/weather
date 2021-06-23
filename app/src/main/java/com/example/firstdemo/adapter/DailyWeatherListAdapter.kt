package com.example.firstdemo.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.example.firstdemo.R
import com.example.firstdemo.Transform
import com.example.firstdemo.data.remote.Daily
import com.example.firstdemo.databinding.WeeklyWeatherListItemNewBinding
import com.example.firstdemo.shared_pereference.SettingsShared
import java.text.SimpleDateFormat
import java.util.*

class DailyWeatherListAdapter(private var city: String) : ListAdapter<Daily, DailyWeatherListAdapter.MyViewHolder>(DailyWeatherDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, city)
    }



    class MyViewHolder private constructor(val binding: WeeklyWeatherListItemNewBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Daily, city : String) {
            binding.daily = item
            binding.dailtCityText.text = city
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup) : MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WeeklyWeatherListItemNewBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }
}

class DailyWeatherDiffCallBack : DiffUtil.ItemCallback<Daily>() {
    override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
        return oldItem == newItem
    }

}