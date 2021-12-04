package com.itheima.sunnyweather.logic.network

import com.itheima.sunnyweather.SunnyWeatherApplication
import com.itheima.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @GET get请求小括号内为
 */
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String ):Call<PlaceResponse>
}