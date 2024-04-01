package com.example.galleryapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationDetails(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val logo300x300: String,
    val description: String,
    val streams: List<Stream>
)