package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService

class EditPeriodViewModel : ViewModel() {

    private var beginDate: MutableLiveData<Long> = MutableLiveData()
    private var endDate: MutableLiveData<Long> = MutableLiveData()
    private var multiple: MutableLiveData<Float> = MutableLiveData()

    fun getBeginLD(): MutableLiveData<Long> = beginDate
    fun getEndLD(): MutableLiveData<Long> = endDate
    fun getMultiplyLD(): MutableLiveData<Float> = multiple

    fun getStartDate() =
        if (beginDate.value != null) {
            beginDate.value!!
        }
        else {
            System.currentTimeMillis()
    }

    fun getEndDate() =
        if (endDate.value != null) {
            endDate.value!!
        }
        else {
            System.currentTimeMillis()
        }

    fun setBeginDate(value: Long) {
        beginDate.value = value
    }

    fun setEndDate(value: Long) {
        endDate.value = value
    }

    fun setMultiple(value: Float) {
        multiple.value = value
    }

    fun createPeriodOfService(id : Int): PeriodOfService? {
        return if (beginDate.value != null
            && endDate.value != null
            && multiple.value != null
        ) {
            PeriodOfService(
                id,
                beginDate.value!!,
                endDate.value!!,
                multiple.value!!
            )
        } else null
    }

    fun setPeriod(period: PeriodOfService) {
        beginDate.value =  period.beginPeriod
        endDate.value = period.endPeriod
        multiple.value = period.multiple
    }
}