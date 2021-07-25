package com.dmabram15.lenghtofservice.presentation.viewModel.stringproviders

interface MessageStringProvider {
    fun getCrossingMessage() : String
    fun getNotAllFieldsFilled() : String
    fun getSuccessMessage() : String
}