package com.example.galleryapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.galleryapp.database.dao.StationDao
import com.example.galleryapp.database.dao.StationDetailsDao
import com.example.galleryapp.model.RadioStation
import com.example.galleryapp.model.StationDetails

@Database(entities = [RadioStation::class, StationDetails::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stationDao(): StationDao
    abstract fun stationDetailsDao(): StationDetailsDao
}