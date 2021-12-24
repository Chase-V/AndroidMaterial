package com.example.androidmaterial.viewModel

import com.example.androidmaterial.repository.PictureOfTheDayResponseData

sealed class PictureOfTheDayState{
    data class Success(val pictureOfTheDayResponseData: PictureOfTheDayResponseData):PictureOfTheDayState()
    data class Loading(val progress: Int?):PictureOfTheDayState()
    data class Error(val error: Throwable):PictureOfTheDayState()
}
