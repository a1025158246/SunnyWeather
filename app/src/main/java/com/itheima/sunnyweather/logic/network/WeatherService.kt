package com.itheima.sunnyweather.logic.network

import com.itheima.sunnyweather.SunnyWeatherApplication
import com.itheima.sunnyweather.logic.model.DailyResponse
import com.itheima.sunnyweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherService {

    //http://api.caiyunapp.com/v2.5/{token}/{lng},{lat}/realtime.json
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat")lat:String): Call<RealtimeResponse>

    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String,@Path("lat")lat:String): Call<DailyResponse>

}