package com.example.androidmaterial.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmaterial.BuildConfig
import com.example.androidmaterial.repository.PictureOfTheDayResponseData
import com.example.androidmaterial.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayState> = MutableLiveData(),
    private val retrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
):ViewModel() {
    fun getData():LiveData<PictureOfTheDayState>{
        return liveDataForViewToObserve
    }

    fun sendServerRequest(){
        liveDataForViewToObserve.value = PictureOfTheDayState.Loading(0)
        val apikey:String = BuildConfig.NASA_API_KEY
        if (apikey.isBlank()){
            liveDataForViewToObserve.value = PictureOfTheDayState.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apikey).enqueue(callback)
        }
    }

    private val callback = object : Callback<PictureOfTheDayResponseData>{
        override fun onResponse(
            call: Call<PictureOfTheDayResponseData>,
            response: Response<PictureOfTheDayResponseData>
        ) {
            if(response.isSuccessful&&response.body()!=null){
                liveDataForViewToObserve.value = PictureOfTheDayState.Success(response.body()!!)
            } else {
                //TODO("ошибка")
            }
        }

        override fun onFailure(call: Call<PictureOfTheDayResponseData>, t: Throwable) {
            //TODO("ошибка")
        }

    }

}