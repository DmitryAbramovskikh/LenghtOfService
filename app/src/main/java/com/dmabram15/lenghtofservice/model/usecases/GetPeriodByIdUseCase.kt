package com.dmabram15.lenghtofservice.model.usecases

import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.repository.Repository

class GetPeriodByIdUseCase {
    fun execute(repository: Repository, periodId : Int) : Period {
        return repository.getPeriodById(periodId)
    }
}