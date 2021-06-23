package com.example.firstdemo.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.example.firstdemo.R
import com.example.firstdemo.Transform
import com.example.firstdemo.data.remote.Daily
import com.example.firstdemo.data.remote.Hourly
import com.example.firstdemo.databinding.HourlyWeatherListItemBinding
import com.example.firstdemo.shared_pereference.SettingsShared
import java.text.SimpleDateFormat
import java.util.*

class HourlyWeatherListAdapter() : ListAdapter<Hourly ,HourlyWeatherListAdapter.HourlyViewHolder>(HourlyWeatherDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyViewHolder {
        return HourlyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

    }

    class HourlyViewHolder private  constructor (val binding : HourlyWeatherListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item : Hourly) {
            binding.hourly = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup)  : HourlyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = HourlyWeatherListItemBinding.inflate(layoutInflater, parent, false)
                return HourlyViewHolder(binding)

            }
        }

    }
}

class HourlyWeatherDiffCallBack : DiffUtil.ItemCallback<Hourly>() {
    override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
        return oldItem == newItem
    }
}