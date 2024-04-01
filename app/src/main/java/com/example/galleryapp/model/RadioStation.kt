package com.example.galleryapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RadioStation(
    @PrimaryKey val id: String,
    val name: String,
    val country: String,
    val logo100x100: String,
    val streams: List<Stream>
)
