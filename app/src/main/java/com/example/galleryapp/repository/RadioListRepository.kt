package com.example.galleryapp.repository

import android.content.Context
import com.example.galleryapp.database.dao.StationDao
import com.example.galleryapp.model.RadioStation
import com.example.galleryapp.network.ApiService
import com.example.galleryapp.util.isOnline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RadioListRepository(private val apiService: ApiService,
                          private val stationDao: StationDao,
                          private val context: Context) {
    fun getStations(systemName: String, count: Int, offset: Int): Flow<List<RadioStation>> = flow {
        val isConnected = isOnline(context)
        val localData = stationDao.getAllStations().first()
        if (localData.isEmpty() || isConnected) {
            try {
                val response = apiService.getStations(systemName, count, offset)
                stationDao.insertStations(response.playables.map {
                    RadioStation(it.id, it.name, it.country, it.logo100x100, it.streams)
                })
                emit(stationDao.getAllStations().first())
            } catch (e: Exception) {
                if (localData.isNotEmpty()) {
                    emit(localData) // Fallback to local data if online fetch fails
                }
                e.printStackTrace()
            }
        } else {
            // if data exists locally and no internet connection, use local data
            emit(localData)
        }
    }.flowOn(Dispatchers.IO)
}