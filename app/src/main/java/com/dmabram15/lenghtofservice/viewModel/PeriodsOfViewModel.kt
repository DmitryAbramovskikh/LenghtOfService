package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService

class PeriodsOfViewModel : ViewModel() {

    private val periodsLiveData = MutableLiveData<ArrayList<PeriodOfService>>()

    fun getPeriods() : MutableLiveData<ArrayList<PeriodOfService>> = periodsLiveData
}