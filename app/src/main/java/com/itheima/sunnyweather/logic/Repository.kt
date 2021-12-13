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

// TODO: 仓库层  主要工作判断调用方请求的数据是应该从本地数据源中获取还是从网络数据源中获取
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
        //发起网络请求 获得数据 左边这个隔断的箭头表示这个执行一个挂起函数
        val placeResponse = SunnyWeatherNetwork.searchPlaces(query)
        //如果接受到请求成功信号"ok"
        if (placeResponse.status == "ok") {
            //包装数据 将返回值传给result
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
        //coroutineScope创建一个协程作用域 在其中使用async 保证两个网络请求都获得后再执行程序
        coroutineScope {
            val deferredRealtime = async {
                SunnyWeatherNetwork.getRealtimeWeather(lng, lat)
            }
            val deferredDaily = async {
                SunnyWeatherNetwork.getDailyWeather(lng, lat)
            }
            val realtimeResponse = deferredRealtime.await()
            val dailyResponse = deferredDaily.await()
            //如果两个请求都成功 打包请求结果
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
            //类似于setValue()通知数据变化
            emit(result)
        }

    // TODO: 本地数据源
    fun savaPlace(place: Place) = PlaceDao.savaPlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSave()

}