package br.com.rsicarelli.bitcoinapp.data

import com.google.gson.annotations.SerializedName

data class RealtimeBitcoinResponse(
    @SerializedName("bpi")
    val lastBitcoinInfo: BitcoinInfo
)

data class BitcoinInfo(
    @SerializedName("USD")
    val currencyDetail: CurrencyDetail
) {
  fun getPrice() = currencyDetail.value
}

data class CurrencyDetail(
    @SerializedName("rate_float")
    val value: Double
)
