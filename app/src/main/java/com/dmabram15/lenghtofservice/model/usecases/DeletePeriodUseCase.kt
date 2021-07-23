package com.dmabram15.lenghtofservice.model.usecases

import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.repository.Repository

class DeletePeriodUseCase {
    fun execute(repository: Repository, period: Period) : Boolean =
        repository.deletePeriod(period) > 0
}