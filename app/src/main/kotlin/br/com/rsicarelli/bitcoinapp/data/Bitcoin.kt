package br.com.rsicarelli.bitcoinapp.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Bitcoin(
    val value: Double,
    val date: String?,
    val currency: Currency
) {
  fun getDateTime(): Date {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return simpleDateFormat.parse(date)
  }
}

enum class Currency(
    val symbol: String
) {
  USD(symbol = "$"),
  EUR(symbol = "€"),
  GBP(symbol = "£");

  companion object {
    fun fromString(symbol: String): Currency {
      return when (symbol) {
        "USD" -> USD
        "EUR" -> EUR
        else -> GBP
      }
    }
  }

}

