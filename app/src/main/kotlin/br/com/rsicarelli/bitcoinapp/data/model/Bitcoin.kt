package br.com.rsicarelli.bitcoinapp.data.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Bitcoin(
    val value: Double,
    val date: String? = null
) {
  fun getDateTime(): Date {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.parse(date)
  }
}
