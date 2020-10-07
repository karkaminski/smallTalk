package pl.karkaminski.smalltalk.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import org.joda.time.DateTime
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
        private lateinit var api: DateFactAPI
    }

    var dateFact = MutableLiveData<DateFact>()

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(DateFactAPI::class.java)

        setDateFact()
    }

    fun setDateFact() {
        var call = api.getDateFact(DateTime.now().monthOfYear,DateTime.now().dayOfMonth)
        call.enqueue(object : Callback<DateFact> {
            override fun onResponse(
                call: Call<DateFact>,
                response: Response<DateFact>
            ) {
                dateFact.postValue(response.body())
                Log.d(TAG, "onResponse: " + response.body()!!.text)
            }

            override fun onFailure(call: Call<DateFact>, t: Throwable) {
                Log.d(TAG, "onFailure" + t.message)
            }

        })
    }


}