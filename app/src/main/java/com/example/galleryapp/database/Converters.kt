package com.example.galleryapp.database

import androidx.room.TypeConverter
import com.example.galleryapp.model.Stream
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromStreamList(value: List<Stream>?): String {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Stream>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStreamList(value: String?): List<Stream> {
        val gson = Gson()
        val type: Type = object : TypeToken<List<Stream>>() {}.type
        return gson.fromJson(value, type)
    }
}