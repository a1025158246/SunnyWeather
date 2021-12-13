package com.itheima.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

//data格式 见P603返回的json数据

/**
 * PlaceResponse
 * @param status:          places:城市数组
 */
data class PlaceResponse(val status:String,val places:List<Place>)

/**
 * 城市数据
 * @param name:城市名  location:经纬度  address:具体地址
 * @SerializedName 返回的JSON一些字段的命名可能与kotlin命名规范不太一致，用SerializedName可以建立映射关系
 */
data class Place(val name :String, val location: Location,
                 @SerializedName("formatted_address")val address:String)

/**
 * 经纬度数据
 * @param lng:经度  lat:维度
 */
data class Location(val lng:String,val lat:String)