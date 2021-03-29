package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.model.repository.RoomRepository
import com.dmabram15.lenghtofservice.model.repository.RoomRepositoryImpl

class PeriodsOfViewModel : ViewModel() {

    private val periodsLiveData = MutableLiveData<ArrayList<PeriodOfService>>()
    private val repository : RoomRepository = RoomRepositoryImpl()

    fun getPeriods() : MutableLiveData<ArrayList<PeriodOfService>> = periodsLiveData

    fun loadData() {
        Thread{
            periodsLiveData.postValue(repository.getAllPeriods())
        }.start()
    }
}