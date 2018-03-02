package br.com.rsicarelli.bitcoinapp.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String.prettyDate(): String {
  val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

  val date = simpleDateFormat.parse(this)

  val calendar = Calendar.getInstance().apply {
    time = date
  }

  with(calendar) {
    val day = get(Calendar.DAY_OF_MONTH)
    val month = getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

    return "${month.toUpperCase()} $day"
  }
}