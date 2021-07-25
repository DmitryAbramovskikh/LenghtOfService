package com.dmabram15.lenghtofservice.presentation.view.viewmatcher

interface OnInputTextStateChangeListener {
    fun onInputError(message: String)
    fun onInputSuccess()
    fun onInputBegin()
}