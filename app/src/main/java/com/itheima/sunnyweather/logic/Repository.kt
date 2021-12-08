package com.itheima.sunnyweather.logic

import androidx.lifecycle.liveData
import com.itheima.sunnyweather.logic.dao.PlaceDao
import com.itheima.sunnyweather.logic.model.Place
import com.itheima.sunnyweather.logic.model.Weather
import com.itheima.sunnyweather.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {
    /*//Dispatchers.IO 子线程
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
    }*/

    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        //获得返回的请求结果
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
        //返回给了placeLiveData 并触发了placeLiveData.observe
    }

    /*fun refreshWeather(lng:String,lat:String)= liveData(Dispatchers.IO){
        val result = try{
            coroutineScope {
                val deferredRealtime = async{
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if(realtimeResponse.status=="ok"&&dailyResponse.status=="ok"){
                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}"+
                                    "daily responst status is ${dailyResponse.status}"
                        )
                    )
                }
            }
        }catch (e:Exception){
            Result.failure<Weather>(e)
        }
        emit(result)
    }*/

    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather = Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realtimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }

    //fire模仿liveData(Dispatchers.IO) 将复用的try catch emit 写在此处 将有改变的函数体放在block函数对象里 并同样支持lambda
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

    fun savaPlace(place: Place) = PlaceDao.savaPlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSave()

}