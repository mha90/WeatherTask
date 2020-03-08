package me.abulazm.weathertask

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.abulazm.weathertask.data.WeatherRetrofitApi
import me.abulazm.weathertask.data.database.WeatherDatabase
import me.abulazm.weathertask.data.datasource.WeatherLocalDataSource
import me.abulazm.weathertask.data.datasource.WeatherRetrievalRemoteDataSource
import me.abulazm.weathertask.data.datasource.WeatherRetrievalRepository
import me.abulazm.weathertask.data.model.WeatherViewIntent

class MainActivity : AppCompatActivity() {

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.weatherRecyclerView) }
    private val progressView by lazy { findViewById<ProgressBar>(R.id.progressBar) }
    private var viewModel: WeatherViewModel? = null
    private lateinit var repo: WeatherRetrievalRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = WeatherViewModel(createRepository())
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        viewModel?.weatherLiveData?.observe(this, Observer {
            renderView(it)
        })

    }

    private fun renderView(weatherViewIntent: WeatherViewIntent) {
        when {
            weatherViewIntent.error != null -> {
                hideProgress()
                Toast.makeText(this, weatherViewIntent.error, Toast.LENGTH_SHORT).show()
            }
            weatherViewIntent.data != null -> {
                hideProgress()
                showContentView()
                recyclerView.adapter = WeatherListAdapter(listOf(weatherViewIntent.data))
            }
            else -> {
                showProgress()
                hideContentView()
            }
        }
    }

    private fun hideContentView() {
        recyclerView.visibility = View.GONE
    }

    private fun showContentView() {
        recyclerView.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressView.visibility = View.GONE
    }

    private fun showProgress() {
        progressView.visibility = View.VISIBLE
    }


    private fun createRepository(): WeatherRetrievalRepository {
        val weatherDao = WeatherDatabase.invoke(this).weatherDao()
        val localDataSource = WeatherLocalDataSource(weatherDao)
        val localSavingDataSource = WeatherLocalDataSource(weatherDao)
        val remoteDataSource = WeatherRetrievalRemoteDataSource(WeatherRetrofitApi.getService())
        repo = WeatherRetrievalRepository(localDataSource, localSavingDataSource, remoteDataSource, true)
        return repo
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.saveMenu) {
            requestAndSave()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun requestAndSave() {
        repo.localFirst = false
        viewModel?.retrieveWeather()
    }

}
