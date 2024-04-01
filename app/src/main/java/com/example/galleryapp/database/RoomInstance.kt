package com.example.galleryapp.database

import android.content.Context
import androidx.room.Room

fun provideDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(context, AppDatabase::class.java, "radio_database")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideStationDao(database: AppDatabase) = database.stationDao()

fun provideStationDetailsDao(database: AppDatabase) = database.stationDetailsDao()
