package com.dmabram15.lenghtofservice.model.usecases

import com.dmabram15.lenghtofservice.model.Period
import com.dmabram15.lenghtofservice.model.repository.Repository

class GetPeriodsUseCase {
    fun execute(repository: Repository) : List<Period> =
        repository.getAllPeriods()
}