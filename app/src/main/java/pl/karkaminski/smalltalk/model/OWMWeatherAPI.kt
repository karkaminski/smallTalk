package pl.karkaminski.smalltalk.model

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OWMWeatherAPI {

    //    @GET("data/2.5/weather?")
    @GET("data/2.5/weather?appid=779466e62f65bb6ec908016c7000e4c2&units=metric")
    fun getOWMWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<OWMWeather>

}