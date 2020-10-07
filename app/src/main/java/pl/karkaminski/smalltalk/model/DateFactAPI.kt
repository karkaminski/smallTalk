package pl.karkaminski.smalltalk.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface DateFactAPI {

    @GET("{monthNumber}/{dayNumber}/date?json")
    fun getDateFact(
        @Path("monthNumber") montNumber: Int,
        @Path("dayNumber") dayNumber: Int
    ): Call<DateFact>
}