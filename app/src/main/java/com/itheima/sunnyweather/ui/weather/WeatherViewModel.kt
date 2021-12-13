package com.itheima.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.itheima.sunnyweather.logic.Repository
import com.itheima.sunnyweather.logic.model.Location

// TODO: ViewModel UI层持有ViewModel使用refreshWeather时 ViewModel向仓库层提出请求 同时将请求结果通过observe回调
class WeatherViewModel:ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng = ""

    var locationLat = ""

    var placeName = ""

    val weatherLiveData = Transformations.switchMap(locationLiveData){ location ->
        Repository.refreshWeather(location.lng,location.lat)
    }

    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value=Location(lng,lat)
    }

}
