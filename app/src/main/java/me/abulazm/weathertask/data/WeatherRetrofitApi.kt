package me.abulazm.weathertask.data

import com.squareup.moshi.Moshi
import me.abulazm.weathertask.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object WeatherRetrofitApi {

    fun getService(): WeatherRetrofitService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .client(createOkHttpClient())
            .build()
        return retrofit.create(WeatherRetrofitService::class.java)
    }

    private fun createOkHttpClient(): OkHttpClient {
        val loggingInterceptor = createLoggingInterceptor()
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        return httpClient.build()
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}