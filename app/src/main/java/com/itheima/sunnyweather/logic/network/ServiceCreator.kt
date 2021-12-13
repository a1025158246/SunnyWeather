package com.itheima.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//get

/**
 * Retrofit构建器
 */
object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)                                      //url
        .addConverterFactory(GsonConverterFactory.create())     //
        .build()

//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    //val appService = retrofit.create(AppService::class.java)
    //比较麻烦的获取一个Service

    //val appService = ServiceCreator.create(AppService::class.java)
    fun<T> create(serviceClass:Class<T>):T = retrofit.create(serviceClass)
//    fun<T> create(aa:Class<T>):T = retrofit.create(aa)

    //val appService = ServiceCreator.create<AppService>()
    //reified 具体化  允许在内联函数情况下 对泛型使用T::class.java
    inline fun<reified T>create():T = create(T::class.java)


}