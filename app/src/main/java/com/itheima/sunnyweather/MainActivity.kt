package com.itheima.sunnyweather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.itheima.sunnyweather.SunnyWeatherApplication.Companion.context
import com.itheima.sunnyweather.logic.model.Location
import com.itheima.sunnyweather.logic.model.Place
import com.itheima.sunnyweather.ui.weather.WeatherActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*
        val initPlace = Place("衡阳市", Location("112.572016", "26.894216"), "中国 湖南省 衡阳市 蒸湘区 蒸湘区")
        val intent = Intent(context, WeatherActivity::class.java).apply {
            putExtra("location_lng", initPlace.location.lng)
            putExtra("location_lat", initPlace.location.lat)
            putExtra("place_name", initPlace.name)
        }
        startActivity(intent)
        finish()*/
    }
}