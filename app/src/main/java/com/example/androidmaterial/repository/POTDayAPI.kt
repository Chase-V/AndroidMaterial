package com.example.androidmaterial.repository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface POTDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<POTDResponseData>

    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Call<POTDResponseData>

}