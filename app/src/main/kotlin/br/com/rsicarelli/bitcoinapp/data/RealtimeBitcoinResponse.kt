package br.com.rsicarelli.bitcoinapp.data

import com.google.gson.annotations.SerializedName

data class RealtimeBitcoinResponse(
    val bpi: Bpi
)

data class Bpi(
    @SerializedName("USD")
    val usd: CurrencyDetail,
    @SerializedName("GBP")
    val gbp: CurrencyDetail,
    @SerializedName("EUR")
    val eur: CurrencyDetail
)

data class CurrencyDetail(
    val code: String,
    val symbol: String,
    val rate: String,
    val description: String,
    val rate_float: Double
)
