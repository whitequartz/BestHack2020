package com.godelsoft.besthack

import java.util.*

// Предоставляет методы для форматирования даты и времени
// (Существующие методы неудобны)
object CalFormatter {
    // Формирует строку с датой из Calendar
    fun datef(c: Calendar): String {
        val day= {day: Int ->
            if (day < 10) "0$day" else "$day"
        } (c.get(Calendar.DAY_OF_MONTH))
        val month = {month: Int ->
            if (month < 10) "0$month" else "$month"
        } (c.get(Calendar.MONTH) + 1)
        val year = {year: Int ->
            if (year != Calendar.getInstance().get(Calendar.YEAR))
                (year % 100).toString()
            else
                ""
        } (c.get(Calendar.YEAR))
        return "$day.$month${if (year != "") ".$year" else ""}"
    }

    // Формирует строку со временем из Calendar
    fun timef(c: Calendar): String {
        val hours= c.get(Calendar.HOUR_OF_DAY).toString()
        val minutes = {month: Int ->
            if (month < 10) "0$month" else "$month"
        } (c.get(Calendar.MINUTE))
        return "$hours:$minutes"
    }

    // Формирует строку с датой из Date
    fun datef(d: Date): String {
        return datef(getCalendarFromDate(d))
    }

    // Формирует строку со временем из Date
    fun timef(d: Date): String {
        return timef(getCalendarFromDate(d))
    }

    fun getCalendarFromDate(d: Date): Calendar {
        return Calendar.getInstance().also {
            it.time = d
        }
    }

    // Проверяет, представляют ли два календаря один и тот же день
    fun checkDaysEq(c1: Calendar, c2: Calendar): Boolean {
        return c1.get(Calendar.DAY_OF_MONTH) == c2.get(Calendar.DAY_OF_MONTH) &&
                c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH) &&
                c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
    }
}