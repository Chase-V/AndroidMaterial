package com.example.androidmaterial.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PictureOfTheDayRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"
    fun getRetrofitImpl():PictureOfTheDayAPI{

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(
                GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .create()
                    )
            )
            .build()
        return retrofit.create(PictureOfTheDayAPI::class.java)
    }
}