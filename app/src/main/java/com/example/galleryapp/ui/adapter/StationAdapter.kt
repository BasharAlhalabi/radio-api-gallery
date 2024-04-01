package com.example.galleryapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.galleryapp.R
import com.example.galleryapp.model.RadioStation

class StationAdapter(
    private val stations: List<RadioStation>,
    private val onClick: (RadioStation) -> Unit
) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        val station = stations[position]
        holder.bind(station, onClick)
    }

    override fun getItemCount(): Int = stations.size

    class StationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageViewLogo: ImageView = itemView.findViewById(R.id.imageViewLogo)
        private val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        private val cardView: CardView = itemView.findViewById(R.id.cardView)

        fun bind(station: RadioStation, onClick: (RadioStation) -> Unit) {
            textViewName.text = station.name
            loadImageFromUrl(imageViewLogo, station.logo100x100)
            cardView.setOnClickListener { onClick(station) }
        }

        private fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(imageView)
        }
    }
}
