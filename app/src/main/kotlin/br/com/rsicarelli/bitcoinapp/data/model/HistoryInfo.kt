package br.com.rsicarelli.bitcoinapp.data.model

import com.google.gson.annotations.SerializedName

data class HistoryInfo(
    @SerializedName("USD")
    private val currencyDetail: CurrencyDetail
) {
  fun getPrice() = currencyDetail.value
}

data class CurrencyDetail(
    @SerializedName("rate_float")
    val value: Double
)