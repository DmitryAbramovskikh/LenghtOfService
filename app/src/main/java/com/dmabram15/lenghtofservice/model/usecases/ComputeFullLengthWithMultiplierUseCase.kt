package com.dmabram15.lenghtofservice.model.usecases

import com.dmabram15.lenghtofservice.model.Period

class ComputeFullLengthWithMultiplierUseCase {
    fun execute(periods : List<Period>) : Long {
        val iterator = periods.iterator()
        var result = 0L
        while (iterator.hasNext()) {
            val period = iterator.next()
            period.let {
                result += ((it.endPeriod - it.beginPeriod) * it.multiple).toLong()
            }
        }
        return result
    }
}