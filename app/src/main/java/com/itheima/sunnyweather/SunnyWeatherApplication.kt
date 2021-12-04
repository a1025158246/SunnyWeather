package com.itheima.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class SunnyWeatherApplication: Application() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var context:Context

        const val TOKEN = "WIL61zKAD08YTx91"
    }

    override fun onCreate() {
        super.onCreate()
        context=applicationContext
    }

}