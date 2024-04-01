package com.example.galleryapp.model

data class StationListResponse(
    val systemName: String,
    val title: String,
    val playables: List<RadioStation>,
    val displayType: String,
    val count: Int,
    val offset: Int,
    val totalCount: Int
)
