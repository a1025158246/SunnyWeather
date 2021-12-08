package com.itheima.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.itheima.sunnyweather.logic.Repository
import com.itheima.sunnyweather.logic.model.Place

class PlaceViewModel:ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    //switchMap监听searchLiveData  placeLiveData.observe 监听 placeLiveData
    val placeLiveData = Transformations.switchMap(searchLiveData){ query->
        //此处发送网络请求
        Repository.searchPlaces(query)
    }

    //当被调用创建了searchLiveData时 调用↑Repository.searchPlaces(query)
    fun searchPlaces(query:String){
        searchLiveData.value=query
    }

    fun savePlace(place: Place) = Repository.savaPlace(place)

    fun getSavedPlace()=Repository.getSavedPlace()

    fun isPlaceSaved()=Repository.isPlaceSaved()

}