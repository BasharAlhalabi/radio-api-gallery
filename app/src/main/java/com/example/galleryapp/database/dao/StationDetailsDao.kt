package com.example.galleryapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.galleryapp.model.StationDetails

@Dao
interface StationDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStationDetail(stationDetails: StationDetails)

    @Query("SELECT * FROM StationDetails WHERE id = :stationId")
    suspend fun getStationDetailById(stationId: String): StationDetails?
}