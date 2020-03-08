package me.abulazm.weathertask.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import me.abulazm.weathertask.data.MainWeatherModel

@Dao
interface WeatherDao {

    @Query("SELECT * FROM `data`")
    fun getWeather(): List<MainWeatherModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: MainWeatherModel):Long

}