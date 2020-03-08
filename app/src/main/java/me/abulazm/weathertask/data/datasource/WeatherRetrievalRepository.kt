package me.abulazm.weathertask.data.datasource

import me.abulazm.weathertask.data.MainWeatherModel

class WeatherRetrievalRepository(
    private val localDataSource: WeatherRetrievalDataSource,
    private val localSavingDataSource: WeatherSavingDataSource,
    private val remoteDataSource: WeatherRetrievalDataSource,
    var localFirst: Boolean
) : WeatherRetrievalDataSource, WeatherSavingDataSource {

    override suspend fun saveWeather(mainWeatherModel: MainWeatherModel, cityName: String): Long {
        return localSavingDataSource.saveWeather(mainWeatherModel, cityName)
    }

    override suspend fun retrieveWeather(cityName: String?): MainWeatherModel? {
        val city = cityName ?: CITY_NAME
        return if (localFirst) localDataSource.retrieveWeather(cityName) ?: retrieveWeatherFromRemoteSource(city)
        else retrieveWeatherFromRemoteSource(city) ?: localDataSource.retrieveWeather(cityName)
    }

    private suspend fun retrieveWeatherFromRemoteSource(cityName: String): MainWeatherModel? {
        val mainWeatherModel = remoteDataSource.retrieveWeather(cityName)
        return if (mainWeatherModel != null) {
            saveWeather(mainWeatherModel, cityName)
            mainWeatherModel
        } else {
            null
        }
    }

    companion object {
        const val CITY_NAME = "london"
    }

}