package com.dmabram15.lenghtofservice.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dmabram15.lenghtofservice.model.PeriodOfService

class EditPeriodViewModel : ViewModel() {

    //TODO реализовать проверку вводимого периода, отсутсвие пересечения с другими периодами

    //TODO реализовать закрытие фрагмента, и проверку на выход без сохранения данных, например флаг

    private var beginDate: MutableLiveData<Long> = MutableLiveData()
    private var endDate: MutableLiveData<Long> = MutableLiveData()
    private var multiple: MutableLiveData<Float> = MutableLiveData()

    fun getBeginLD(): MutableLiveData<Long> = beginDate
    fun getEndLD(): MutableLiveData<Long> = endDate
    fun getMultiplyLD(): MutableLiveData<Float> = multiple

    fun setBeginDate(value: Long) {
        beginDate.value = value
    }

    fun setEndDate(value: Long) {
        endDate.value = value
    }

    fun setMultiple(value: Float) {
        multiple.value = value
    }

    fun createPeriodOfService(): PeriodOfService? {
        return if (beginDate.value != null
            && endDate.value != null
            && multiple.value != null
        ) {
            PeriodOfService(
                0,
                beginDate.value!!,
                endDate.value!!,
                multiple.value!!
            )
        } else null
    }
}