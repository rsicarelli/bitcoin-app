package br.com.rsicarelli.bitcoinapp.data.persistance

import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class BitcoinConverter(
    private val gson: Gson
) {

  fun convertListToJson(bitcoins: List<Bitcoin>): String {
    return gson.toJson(bitcoins)
  }

  fun convertJsonToList(json: String): List<Bitcoin> {
    return gson.fromJson(json, object : TypeToken<List<Bitcoin>>() {}.type)
  }

}
