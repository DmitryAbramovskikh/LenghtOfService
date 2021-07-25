package com.dmabram15.lenghtofservice.model.listeners

import com.dmabram15.lenghtofservice.model.Period

//Итерфейс матчера пересечений диапазонов
// (сохраняемого и уже имеющегося в базе)
interface OnMatcherEventListener {
    //Начальная дата пересекается с ренее введенным периодом
    fun hasBeginCross(crossedPeriod: Period, date : Long)
    //Конечная дата пересекается с ранее введенным периодом
    fun hasEndCross(crossedPeriod: Period, date : Long)
    //Ранее введенный период полностью входит в диапазон между начальной и конечной датой
    fun hasIncludingCross(crossedPeriod: Period)
    //Перечений нет
    fun success()
}