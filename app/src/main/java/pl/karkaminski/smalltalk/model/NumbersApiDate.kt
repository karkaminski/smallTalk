package pl.karkaminski.smalltalk.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.http.GET

interface NumbersApiDate {

    @GET("9/21/date?json")
    fun getDateFact() : Call<DateFact>
}