package com.example.galleryapp.repository

import android.content.Context
import com.example.galleryapp.database.dao.StationDetailsDao
import com.example.galleryapp.model.StationDetails
import com.example.galleryapp.network.ApiService
import com.example.galleryapp.util.isOnline
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.Dispatchers

class DetailsRepository(
    private val apiService: ApiService,
    private val stationDetailsDao: StationDetailsDao,
    private val context: Context
) {
    fun fetchStationDetailsById(stationId: String): Flow<StationDetails?> = flow {
        val isConnected = isOnline(context)
        val localData = stationDetailsDao.getStationDetailById(stationId)

        if (localData != null && !isConnected) {
            // if data exists locally and no internet connection, use local data
            emit(localData)
        } else {
            try {
                // Try to fetch new data from network if connected
                val stationDetails = apiService.getStationById(stationId).first()
                stationDetailsDao.insertStationDetail(stationDetails)
                emit(stationDetails)
            } catch (e: Exception) {
                // If fetching fails, fallback to local data if available
                if (localData != null) {
                    emit(localData)
                } else {
                    emit(null)
                }
                e.printStackTrace()
            }
        }
    }.flowOn(Dispatchers.IO)
}
