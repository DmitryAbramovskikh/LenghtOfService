package com.dmabram15.lenghtofservice.model

class PeriodBuilder {
    private var id: Int = 0
    private var beginOfPeriod: Long = 0L
    private var endOfPeriod: Long = 0L
    private var multiple: Float = 0f

    fun setId(id: Int): PeriodBuilder {
        this.id = id
        return this
    }

    fun setBeginPeriod(beginPeriod: Long): PeriodBuilder {
        this.beginOfPeriod = beginPeriod
        return this
    }

    fun setEndPeriod(endPeriod: Long): PeriodBuilder {
        this.endOfPeriod = endPeriod
        return this
    }

    fun setMultiple(multiple: Float): PeriodBuilder {
        this.multiple = multiple
        return this
    }

    fun build(): Period = Period(
        this.id,
        this.beginOfPeriod,
        this.endOfPeriod,
        this.multiple
    )

    fun isReadyToBuild(): Boolean =
        when {
            this.id == 0 -> false
            this.beginOfPeriod == 0L -> false
            this.endOfPeriod == 0L -> false
            this.multiple == 0f -> false
            else -> true
        }

    fun getBeginDate() = beginOfPeriod
    fun getEndDate() = endOfPeriod
}