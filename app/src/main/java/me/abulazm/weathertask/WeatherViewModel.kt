package me.abulazm.weathertask

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import me.abulazm.weathertask.data.datasource.WeatherRetrievalRepository
import me.abulazm.weathertask.data.model.WeatherViewIntent

class WeatherViewModel(private val repository: WeatherRetrievalRepository) : ViewModel() {

    private val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    // replace with ViewIntentModel
    val weatherLiveData: MutableLiveData<WeatherViewIntent> by lazy {
        MutableLiveData<WeatherViewIntent>().also { retrieveWeather() }
    }

    fun retrieveWeather() {

        coroutineScope.launch {
            weatherLiveData.postValue(WeatherViewIntent(null, null, true))

            val weatherModel = repository.retrieveWeather(null)

            if (weatherModel != null) {
                weatherLiveData.postValue(WeatherViewIntent(weatherModel, null, false))
            } else {
                // handle empty state
                weatherLiveData.postValue(WeatherViewIntent(null, "No data to show", false))
            }

        }
    }


    override fun onCleared() {
        if (coroutineScope.isActive) {
            coroutineScope.cancel(null)
        }
        super.onCleared()
    }
}