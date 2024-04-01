package com.example.galleryapp.network

import com.example.galleryapp.model.StationDetails
import com.example.galleryapp.model.StationListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("stations/list-by-system-name")
    suspend fun getStations(
        @Query("systemName") systemName: String,
        @Query("count") count: Int,
        @Query("offset") offset: Int
    ): StationListResponse

    @GET("stations/details")
    suspend fun getStationById(@Query("stationIds") stationId: String): List<StationDetails>
}


