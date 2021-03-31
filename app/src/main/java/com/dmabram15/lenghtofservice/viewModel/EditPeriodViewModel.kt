package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService

class EditPeriodViewModel : ViewModel() {
    private var beginDate: MutableLiveData<Long> = MutableLiveData()
    private var endDate: MutableLiveData<Long> = MutableLiveData()
    private var multiple: MutableLiveData<Float> = MutableLiveData()
    private var period: MutableLiveData<PeriodOfService> = MutableLiveData()

    fun getBeginLD(): MutableLiveData<Long> = beginDate
    fun getEndLD(): MutableLiveData<Long> = endDate
    fun getMultiplyLD(): MutableLiveData<Float> = multiple
    fun getPeriod(): MutableLiveData<PeriodOfService> = period

    fun setBeginDate(value: Long) {
        beginDate.value = value
    }

    fun setEndDate(value: Long) {
        endDate.value = value
    }

    fun setMultiple(value: Float) {
        multiple.value = value
    }

    private fun setPeriod(value: PeriodOfService) {
        period.value = value
    }

    fun applyClick() {
        if (beginDate.value != null
            && endDate.value != null
            && multiple.value != null
        ) {
            val periodOfService = PeriodOfService(
                0,
                beginDate.value!!,
                endDate.value!!,
                multiple.value!!
            )
            setPeriod(periodOfService)
        }
    }
}