package pl.karkaminski.smalltalk.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DateFactRepository {

    companion object {

        private const val TAG = "DateFactRepository"
        private const val BASE_URL = "http://numbersapi.com/"

        private lateinit var retrofit: Retrofit
        private lateinit var numbersApi: NumbersApiDate
    }

    lateinit var dateFact: DateFact

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        numbersApi = retrofit.create(NumbersApiDate::class.java)

        setDateFact()
    }

    fun setDateFact() {
        dateFact = DateFact()
        var call = numbersApi.getDateFact()
        call.enqueue(object : Callback<DateFact> {
            override fun onResponse(
                call: Call<DateFact>,
                response: Response<DateFact>
            ) {
                dateFact = response.body()!!
                Log.d(TAG, "onResponse: " + dateFact.text)

            }

            override fun onFailure(call: Call<DateFact>, t: Throwable) {
                Log.d(TAG, "onFailure" + t.message)
            }

        })
    }


}