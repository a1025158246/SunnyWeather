package com.itheima.sunnyweather.logic.model

/**
 * @param realtime:RealtimeResponse.Realtime
 * @param daily:DailyResponse.Daily
 */
data class Weather(val realtime:RealtimeResponse.Realtime,val daily:DailyResponse.Daily)