package me.abulazm.weathertask.ext

import me.abulazm.weathertask.data.MainWeatherModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TEMPERATURE_DECIMAL_FORMAT = "###.#"

fun MainWeatherModel.formatDate(): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy")
    val date = Date()
    date.time = timeStamp
    return dateFormat.format(date)
}

fun Double.convertTemperatureToCelsius(): String {
    val decimalFormat = DecimalFormat(TEMPERATURE_DECIMAL_FORMAT)
    val temperature = this - 273.15

    return decimalFormat.format(temperature)
}