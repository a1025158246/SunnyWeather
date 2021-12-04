package com.itheima.sunnyweather.logic

import androidx.lifecycle.liveData
import com.itheima.sunnyweather.logic.model.Place
import com.itheima.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

object Repository {
    //Dispatchers.IO 子线程
    fun searchPlaces(query:String)= liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            if(placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}