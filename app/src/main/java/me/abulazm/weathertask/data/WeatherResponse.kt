package me.abulazm.weathertask.data

class WeatherResponse(
    val base: String,
    val weather: List<Weather>,
    val coord: Coordination,
    val wind: Wind,
    val main: MainWeatherModel
)

class Wind(val speed: Double, val deg: Double)

class Weather(val id: Int, val main: String, val description: String)

class Coordination(val lon: Double, val lat: Double)