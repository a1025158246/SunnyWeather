package com.itheima.sunnyweather.logic.dao

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.google.gson.Gson
import com.itheima.sunnyweather.SunnyWeatherApplication
import com.itheima.sunnyweather.logic.model.Place

object PlaceDao {
    fun savaPlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    fun isPlaceSave() = sharedPreferences().contains("place")

    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", MODE_PRIVATE)
}