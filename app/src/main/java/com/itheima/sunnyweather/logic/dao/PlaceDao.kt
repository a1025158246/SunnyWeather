package com.itheima.sunnyweather.logic.dao

import android.content.Context.MODE_PRIVATE
import androidx.core.content.edit
import com.google.gson.Gson
import com.itheima.sunnyweather.SunnyWeatherApplication
import com.itheima.sunnyweather.logic.model.Place

// TODO: 本地数据源
object PlaceDao {
    //将Placa对象转化成json格式进行存储
    fun savaPlace(place: Place) {
        sharedPreferences().edit {
            putString("place", Gson().toJson(place))
        }
    }

    fun getSavedPlace(): Place {
        //取出xml文件中的json格式place值 返回转换后的Place对象
        val placeJson = sharedPreferences().getString("place", "")
        return Gson().fromJson(placeJson, Place::class.java)
    }

    //包含了place则返回true
    fun isPlaceSave() = sharedPreferences().contains("place")

    //创建使用一个名字为sunny_weather的xml文件
    private fun sharedPreferences() =
        SunnyWeatherApplication.context.getSharedPreferences("sunny_weather", MODE_PRIVATE)
}