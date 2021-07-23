package com.dmabram15.lenghtofservice.viewModel.converters

import com.dmabram15.lenghtofservice.viewModel.stringproviders.CalendarStringProvider
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class DateConverter(
    private val calendarStringProvider: CalendarStringProvider
) {
    var calendar = GregorianCalendar()

    fun convert(date: Long): String {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru-RU"))
        return simpleDateFormat.format(Date(date))
    }

    fun convertToLong(date: CharSequence): Long {
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale("ru-RU"))
        return simpleDateFormat.parse(date.toString()).time
    }

    fun convertDifferent(different: Long): String {
        calendar.time = java.util.Date(different)
        val years = calendar.get(Calendar.YEAR) - 1970
        val months = calendar.get(Calendar.MONTH)
        val days = calendar.get(Calendar.DAY_OF_MONTH) - 1
        val yearsText = calendarStringProvider.getYearsText()
        val monthsText = calendarStringProvider.getMonthsText()
        val daysText = calendarStringProvider.getDaysText()

        return "$years $yearsText $months $monthsText $days $daysText"
    }
}