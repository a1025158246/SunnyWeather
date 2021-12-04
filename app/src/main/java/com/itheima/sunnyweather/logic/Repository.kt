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
            //发起网络请求 获得数据
            val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
            //如果接受到请求成功新号"ok"
            if(placeResponse.status == "ok"){
                val places = placeResponse.places
                //包装数据 将返回值传给result
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
//        通知数据变化
        emit(result)
    }
}