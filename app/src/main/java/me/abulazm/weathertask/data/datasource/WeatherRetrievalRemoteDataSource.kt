package me.abulazm.weathertask.data.datasource

import me.abulazm.weathertask.data.MainWeatherModel
import me.abulazm.weathertask.data.WeatherRetrofitService

class WeatherRetrievalRemoteDataSource(private val retrofitService: WeatherRetrofitService) :
    WeatherRetrievalDataSource {

    override suspend fun retrieveWeather(cityName: String?): MainWeatherModel? {
        return cityName?.let { retrofitService.getWeatherList(it).main }
    }

}