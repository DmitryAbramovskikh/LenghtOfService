package com.dmabram15.lenghtofservice.model.usecases

import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.repository.Repository

class SavePeriodUseCase {
    fun execute(repository: Repository, period: Period): Boolean =
        repository.savePeriod(period) > 0
}