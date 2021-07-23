package com.dmabram15.lenghtofservice.viewModel.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dmabram15.lenghtofservice.model.repository.Repository
import com.dmabram15.lenghtofservice.model.usecases.ComputeFullLengthWithMultiplierUseCase
import com.dmabram15.lenghtofservice.model.usecases.ComputeFullLengthWithoutMultiplierUseCase
import com.dmabram15.lenghtofservice.model.usecases.GetPeriodsUseCase
import com.dmabram15.lenghtofservice.viewModel.converters.DateConverter
import com.dmabram15.lenghtofservice.viewModel.stringproviders.CalendarStringProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LengthOfServiceViewModel : ViewModel() {

    val lengthWithMultiplier  = MutableLiveData<String>()
    val lengthWithoutMultiplier  = MutableLiveData<String>()

    fun fetchLengths(repository: Repository, stringProvider : CalendarStringProvider) {
        //Пока так до перехода на di насоздавал зависимостей
        viewModelScope.launch(Dispatchers.IO){
            val periods = GetPeriodsUseCase().execute(repository)
            val lengthInMillisMultiplier = ComputeFullLengthWithMultiplierUseCase().execute(periods)
            lengthWithMultiplier.postValue(DateConverter(stringProvider).convert(lengthInMillisMultiplier))

            val lengthInMillis = ComputeFullLengthWithoutMultiplierUseCase().execute(periods)
            lengthWithoutMultiplier.postValue(DateConverter(stringProvider).convert(lengthInMillis))
        }
    }
}