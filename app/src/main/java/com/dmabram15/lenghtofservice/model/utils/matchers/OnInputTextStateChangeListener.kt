package com.dmabram15.lenghtofservice.model.utils.matchers

interface OnInputTextStateChangeListener {
    fun onInputError(message: String)
    fun onInputSuccess()
    fun onInputBegin()
}