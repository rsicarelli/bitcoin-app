package br.com.rsicarelli.bitcoinapp.data.responses

import com.google.gson.annotations.SerializedName

data class BitcoinHistoryResponse(
    @SerializedName("bpi")
    val history: Map<String, Double>
)