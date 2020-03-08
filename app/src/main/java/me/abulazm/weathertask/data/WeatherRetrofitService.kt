package me.abulazm.weathertask.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherRetrofitService {

    @GET("weather?appid=2c65d5c76d45a7b92619faf46315c527")
    suspend fun getWeatherList(@Query("q") cityName: String): WeatherResponse

}