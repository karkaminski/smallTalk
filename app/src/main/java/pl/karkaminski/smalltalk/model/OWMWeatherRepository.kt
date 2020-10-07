package pl.karkaminski.smalltalk.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OWMWeatherRepository {

    companion object {
        private const val TAG = "OWMWeatherRepository"
        private const val BASE_URL = "https://api.openweathermap.org/"

        private lateinit var retrofit: Retrofit
        private lateinit var api: OWMWeatherAPI
    }

    var currentWeather = MutableLiveData<OWMWeather>()

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(OWMWeatherAPI::class.java)

        getCurrentWeather()
    }

    private fun getCurrentWeather() {
        //    @GET(lat=50.05&lon=19.99)
        var call = api.getOWMWeather(50.05,19.99  )
        call.enqueue(object : Callback<OWMWeather> {
            override fun onResponse(
                call: Call<OWMWeather>,
                response: Response<OWMWeather>
            ) {
                Log.d(TAG, "onResponse: ")
                var weather = response.body()
                weather!!.iconURL =
                    "http://openweathermap.org/img/wn/" + weather.weather!![0].icon + "@2x.png"
                currentWeather.postValue(weather)
            }

            override fun onFailure(call: Call<OWMWeather>, t: Throwable) {
                Log.d(TAG, "onFailure: " + t.message)
            }
        })
    }
}