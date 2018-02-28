package br.com.rsicarelli.bitcoinapp.data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateRangeCreator(
    private val currentCalendar: Calendar
) {

  fun getDateRangeFromNow(daysBefore: Int): Pair<String, String> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val currentTime = dateFormat.format(currentCalendar.time)

    currentCalendar.add(Calendar.DATE, daysBefore)

    val beforeTimeDateRange = dateFormat.format(Date(currentCalendar.timeInMillis))

    return Pair(beforeTimeDateRange, currentTime)
  }
}