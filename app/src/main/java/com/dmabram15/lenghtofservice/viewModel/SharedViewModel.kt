package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService
import com.dmabram15.lenghtofservice.model.repository.RoomRepository
import com.dmabram15.lenghtofservice.model.repository.RoomRepositoryImpl
import com.dmabram15.lenghtofservice.view.adapters.OnPeriodDeleteClickListener

class SharedViewModel : ViewModel() {

    private val periodsLiveData = MutableLiveData<ArrayList<PeriodOfService>>()
    private var repository : RoomRepository = RoomRepositoryImpl()

    init {
        loadData()
    }

    fun getPeriods() : MutableLiveData<ArrayList<PeriodOfService>> = periodsLiveData

    fun setPeriod(value: PeriodOfService) {
        periodsLiveData.value?.add(value)
    }

    fun savePeriod(periodOfService: PeriodOfService) {
        Thread{
            repository.savePeriod(periodOfService)
        }.start()
    }

    fun loadData() {
        Thread{
            periodsLiveData.postValue(repository.getAllPeriods())
        }.start()
    }

    fun checkPeriodsCollision(periodOfService : PeriodOfService): Boolean {
        periodsLiveData.value?.let{ periods ->
            for (periodItem : PeriodOfService in periods) {
                if (periodOfService.beginPeriod in periodItem.beginPeriod .. periodItem.endPeriod)
                    return false
                if(periodOfService.endPeriod in periodItem.beginPeriod .. periodItem.endPeriod)
                    return false
                if (periodItem.beginPeriod in periodOfService.beginPeriod .. periodOfService.endPeriod)
                    return false
            }
        }
        return true
    }

    fun getListener(): OnPeriodDeleteClickListener {
        return object : OnPeriodDeleteClickListener {
            override fun delete(periodOfService: PeriodOfService) {
                Thread{
                    repository.deletePeriod(periodOfService)
                }.start()
            }
        }
    }
}