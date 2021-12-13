package com.itheima.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.itheima.sunnyweather.logic.Repository
import com.itheima.sunnyweather.logic.model.Place

class PlaceViewModel:ViewModel() {

//    LiveData还能知晓它绑定的Activity或者Fragment的生命周期,它只会给前台活动的activity回调(这个很厉害).
//    这样你可以放心的在它的回调方法里直接将数据添加到View,而不用担心会不会报错.(你也可以不用费心费力判断Fragment是否还存活)

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