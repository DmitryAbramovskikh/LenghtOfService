package com.dmabram15.lenghtofservice.model

import com.dmabram15.lenghtofservice.App
import com.dmabram15.lenghtofservice.R
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {
    companion object {
        var calendar = GregorianCalendar()
        val context = App.getInstance()

        @JvmStatic
        fun convert(date: Long): String {
            val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy")
            return simpleDateFormat.format(Date(date))
        }

        @JvmStatic
        fun convertDifferent(different: Long): String {
            calendar.time = java.util.Date(different - 1)

            val years = calendar.get(Calendar.YEAR) - 1970
            val months = calendar.get(Calendar.MONTH)
            val days = when (different) {
                0L -> 0
                else -> calendar.get(Calendar.DAY_OF_MONTH)
            }

            val yearsText = getYearsString(years)
            val monthsText = getMonthsString(months)
            val daysText = getDaysString(days)

            return "$years $yearsText $months $monthsText $days $daysText"
        }

        private fun getYearsString(years: Int): String {
            val result: String
            var tempYears = years % 100
            if (tempYears == 0 || (tempYears in 5..20)) {
                result = context.getString(R.string.many_years_converter)
            } else {
                tempYears %= 10
                result = when {
                    tempYears % 10 == 1 -> context.getString(R.string.year_converter)
                    tempYears in 5..10 -> context.getString(R.string.many_years_converter)
                    else -> context.getString(R.string.some_years_converter)
                }
            }
            return result
        }

        private fun getMonthsString(months: Int): String {
            val result: String
            var tempMonths = months % 100
            if (tempMonths == 0 || (tempMonths in 5..20)) {
                result = context.getString(R.string.many_months_converter)
            } else {
                tempMonths %= 10
                result = when {
                    tempMonths % 10 == 1 -> context.getString(R.string.month_converter)
                    tempMonths in 5..10 -> context.getString(R.string.many_months_converter)
                    else -> context.getString(R.string.some_months_converter)
                }
            }
            return result
        }

        private fun getDaysString(days: Int): String {
            val result: String
            var tempDays = days % 100
            if (tempDays == 0 || (tempDays in 5..20)) {
                result = context.getString(R.string.many_days_converter)
            } else {
                tempDays %= 10
                result = when {
                    tempDays % 10 == 1 -> context.getString(R.string.day_converter)
                    tempDays in 5..10 -> context.getString(R.string.many_days_converter)
                    else -> context.getString(R.string.some_days_converter)
                }
            }
            return result
        }
    }
}