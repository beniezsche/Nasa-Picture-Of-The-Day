package com.beniezsche.junoassignment.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.beniezsche.junoassignment.model.APOD
import com.beniezsche.junoassignment.networking.RetrofitClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApodViewModel : ViewModel() {


    private var dataService = RetrofitClient.dataService
    private val API_KEY = "kUJDDDPTo8Kf6IWq4uIJbKssPwfwubabkDp5PNKP"
    private val TAG = this.javaClass.name


    fun getPhotoOfDay(): MutableLiveData<APOD> {

        val data = MutableLiveData<APOD>()

        dataService.getPhotoOfTheDay(API_KEY).enqueue(object : Callback<APOD> {
            override fun onFailure(call: Call<APOD>, t: Throwable) {
                Log.d(TAG, t.message)
                data.value = null
            }

            override fun onResponse(call: Call<APOD>, response: Response<APOD>) {

                if (response.isSuccessful) {
                    Log.d(TAG, Gson().toJson(response.body()))
                    data.value = response.body()
                }
            }


        })

        return data
    }

    fun getPhotoOfDayByDate(date: String): MutableLiveData<APOD> {

        val data = MutableLiveData<APOD>()

        dataService.getPhotoOfTheDayByDate(API_KEY, date).enqueue(object : Callback<APOD> {
            override fun onFailure(call: Call<APOD>, t: Throwable) {
                Log.d(TAG, t.message)
                data.value = null
            }

            override fun onResponse(call: Call<APOD>, response: Response<APOD>) {

                if (response.isSuccessful) {
                    Log.d(TAG, date + ":" + Gson().toJson(response.body()))
                    data.value = response.body()
                }
            }


        })

        return data
    }


}