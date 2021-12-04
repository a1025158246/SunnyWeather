package com.itheima.sunnyweather.logic.network

import com.itheima.sunnyweather.SunnyWeatherApplication
import com.itheima.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * 用于访问彩云天气城市搜索API的Retrofit接口
 * @GET get请求 参数query由注解@Query声明为动态指定参数  TOKEN lang 为固定参数 直接写在@GET注解内
 * 返回值为Call<PlaceResponse>  Retrofit会将返回的JSON数据自动解析成PlaceResponse对象
 */
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String ):Call<PlaceResponse>
}