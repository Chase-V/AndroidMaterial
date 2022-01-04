package com.example.androidmaterial.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmaterial.BuildConfig
import com.example.androidmaterial.repository.POTDResponseData
import com.example.androidmaterial.repository.POTDRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class POTDViewModel(
    private val liveDataForViewToObserve: MutableLiveData<POTDState> = MutableLiveData(),
    private val retrofitImpl: POTDRetrofitImpl = POTDRetrofitImpl()
) : ViewModel() {
    fun getData(): LiveData<POTDState> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest() {
        liveDataForViewToObserve.value = POTDState.Loading(0)
        val apikey: String = BuildConfig.NASA_API_KEY
        if (apikey.isBlank()) {
            liveDataForViewToObserve.value = POTDState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apikey).enqueue(callback)
        }
    }

    private val callback = object : Callback<POTDResponseData> {
        override fun onResponse(
            call: Call<POTDResponseData>,
            response: Response<POTDResponseData>
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataForViewToObserve.value = POTDState.Success(response.body()!!)
            } else {
                liveDataForViewToObserve.value = POTDState.Error(Throwable("NASA не ответило"))
            }
        }

        override fun onFailure(call: Call<POTDResponseData>, t: Throwable) {
            liveDataForViewToObserve.value = POTDState.Error(Throwable("NASA не ответило"))
        }

    }

}