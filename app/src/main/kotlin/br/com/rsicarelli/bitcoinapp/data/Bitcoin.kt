package br.com.rsicarelli.bitcoinapp.data

data class Bitcoin(
    val value: Double,
    val date: String?,
    val currency: Currency
)

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

