package br.com.rsicarelli.bitcoinapp.data.responses

import br.com.rsicarelli.bitcoinapp.data.model.HistoryInfo
import com.google.gson.annotations.SerializedName

data class RealtimeBitcoinResponse(
    @SerializedName("bpi")
    val historyInfo: HistoryInfo
)

