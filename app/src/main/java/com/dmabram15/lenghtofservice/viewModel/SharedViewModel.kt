package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.model.repository.RoomRepository
import com.dmabram15.lenghtofservice.model.repository.RoomRepositoryImpl

class SharedViewModel : ViewModel() {


    private var period: MutableLiveData<PeriodOfService> = MutableLiveData()
    private val periodsLiveData = MutableLiveData<ArrayList<PeriodOfService>>()
    private val repository : RoomRepository = RoomRepositoryImpl()

    fun getPeriods() : MutableLiveData<ArrayList<PeriodOfService>> = periodsLiveData

    fun getCurrentPeriod(): MutableLiveData<PeriodOfService> = period

    fun setPeriod(value: PeriodOfService) {
        periodsLiveData.value?.add(value)
        savePeriod(value)
    }

    private fun savePeriod(periodOfService: PeriodOfService) {
        periodsLiveData.value?.add(periodOfService)
        Thread{
            repository.savePeriod(periodOfService)
        }.start()
    }

    fun loadData() {
        Thread{
            periodsLiveData.postValue(repository.getAllPeriods())
        }.start()
    }

    fun checkPeriodsCollision(
        periodOfService: PeriodOfService,
        periodsList: ArrayList<PeriodOfService>
    ): Boolean {
        for (periodItem : PeriodOfService in periodsList) {
            if (periodOfService.beginPeriod in periodItem.beginPeriod .. periodItem.endPeriod)
                return false
            if(periodOfService.endPeriod in periodItem.beginPeriod .. periodItem.endPeriod)
                return false
        }
        return true
    }
}