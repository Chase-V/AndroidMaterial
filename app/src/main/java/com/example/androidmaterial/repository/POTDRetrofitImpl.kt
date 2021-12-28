package com.example.androidmaterial.repository

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class POTDRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"
    fun getRetrofitImpl():POTDayAPI{

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
        return retrofit.create(POTDayAPI::class.java)
    }
}