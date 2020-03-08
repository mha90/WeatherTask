package me.abulazm.weathertask.data.datasource

import me.abulazm.weathertask.data.MainWeatherModel

interface WeatherSavingDataSource {

    suspend fun saveWeather(mainWeatherModel: MainWeatherModel, cityName:String):Long

}