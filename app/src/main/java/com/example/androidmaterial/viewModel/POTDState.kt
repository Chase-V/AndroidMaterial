package com.example.androidmaterial.viewModel

import com.example.androidmaterial.repository.POTDResponseData

sealed class POTDState{
    data class Success(val pictureOfTheDayResponseData: POTDResponseData):POTDState()
    data class Loading(val progress: Int?):POTDState()
    data class Error(val error: Throwable):POTDState()
}
