package com.dmabram15.lenghtofservice.model

class PeriodBuilder {
    private var id: Int = 0
    private var beginPeriod: Long = 0L
    private var endPeriod: Long = 0L
    private var multiple: Float = 0f

    fun setId(id: Int): PeriodBuilder {
        this.id = id
        return this
    }

    fun setBeginPeriod(beginPeriod: Long): PeriodBuilder {
        this.beginPeriod = beginPeriod
        return this
    }

    fun setEndPeriod(endPeriod: Long): PeriodBuilder {
        this.endPeriod = endPeriod
        return this
    }

    fun setMultiple(multiple: Float): PeriodBuilder {
        this.multiple = multiple
        return this
    }

    fun build(): Period = Period(
        this.id,
        this.beginPeriod,
        this.endPeriod,
        this.multiple
    )

    fun isReadyToBuild(): Boolean =
        when {
            this.id == 0 -> false
            this.beginPeriod == 0L -> false
            this.endPeriod == 0L -> false
            this.multiple == 0f -> false
            else -> true
        }
}