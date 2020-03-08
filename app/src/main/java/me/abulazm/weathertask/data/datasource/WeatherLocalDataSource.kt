package me.abulazm.weathertask.data.datasource

import me.abulazm.weathertask.data.MainWeatherModel
import me.abulazm.weathertask.data.database.WeatherDao

class WeatherLocalDataSource(private val weatherDao: WeatherDao) :
    WeatherRetrievalDataSource,
    WeatherSavingDataSource {

    override suspend fun saveWeather(mainWeatherModel: MainWeatherModel, cityName: String): Long {
        mainWeatherModel.timeStamp = System.currentTimeMillis()
        mainWeatherModel.city = cityName
        return weatherDao.insertWeather(mainWeatherModel)
    }

    override suspend fun retrieveWeather(cityName: String?): MainWeatherModel? {
        val list = weatherDao.getWeather()
        return if (list.isNullOrEmpty()) null else list.first()
    }

}