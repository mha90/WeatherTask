package me.abulazm.weathertask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import me.abulazm.weathertask.data.MainWeatherModel
import me.abulazm.weathertask.ext.convertTemperatureToCelsius
import me.abulazm.weathertask.ext.formatDate

class WeatherListAdapter(private val items: List<MainWeatherModel>) :
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holderWeather: WeatherViewHolder, position: Int) {
        holderWeather.bind(items[position])
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val temperatureTextView by lazy { itemView.findViewById<TextView>(R.id.temperatureTextView) }
        private val cityTextView by lazy { itemView.findViewById<TextView>(R.id.cityTextView) }
        private val dateTextView by lazy { itemView.findViewById<TextView>(R.id.dateTextView) }

        fun bind(mainWeatherModel: MainWeatherModel) {

            temperatureTextView.text = mainWeatherModel.temp.convertTemperatureToCelsius()
            cityTextView.text = mainWeatherModel.city.capitalize()
            dateTextView.text = mainWeatherModel.formatDate()

        }

    }

}