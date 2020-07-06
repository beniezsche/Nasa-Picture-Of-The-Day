package com.beniezsche.junoassignment.networking

import com.beniezsche.junoassignment.model.APOD
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DataService {


    @GET("apod")
    fun getPhotoOfTheDay(@Query("api_key") api_key: String): Call<APOD>

    @GET("apod")
    fun getPhotoOfTheDayByDate(
        @Query("api_key") api_key: String,
        @Query("date") date: String
    ): Call<APOD>


}