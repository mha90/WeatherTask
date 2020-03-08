package me.abulazm.weathertask.data.datasource

import me.abulazm.weathertask.data.MainWeatherModel

interface WeatherRetrievalDataSource {

    suspend fun retrieveWeather(cityName:String?): MainWeatherModel?
}