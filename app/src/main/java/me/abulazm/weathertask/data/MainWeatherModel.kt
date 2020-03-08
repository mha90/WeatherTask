package me.abulazm.weathertask.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
class MainWeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val temp: Double,
    val pressure: Double,
    val humidity: Double,
    val temp_main: Double,
    val temp_max: Double,
    var timeStamp: Long,
    var city: String
)