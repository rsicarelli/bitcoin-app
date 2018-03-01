package br.com.rsicarelli.bitcoinapp.data.persistance

import android.content.SharedPreferences
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import io.reactivex.Completable
import io.reactivex.Single
import java.lang.Exception

class BitcoinSharedPreferences(
    private val sharedPreferences: SharedPreferences,
    private val bitcoinConverter: BitcoinConverter
) {

  fun saveBitcoinData(bitcoin: Bitcoin): Completable {
    return Completable.fromCallable {
      sharedPreferences
          .edit()
          .putFloat(FIELD_RECENT_DATA, bitcoin.value.toFloat())
          .apply()
    }
  }

  fun getLastBitcoinData(): Single<Bitcoin> {
    return Single.create<Bitcoin> { emitter ->
      val lastBitcoinValue = sharedPreferences.getFloat(FIELD_RECENT_DATA, 0.0f)
      emitter.onSuccess(Bitcoin(value = lastBitcoinValue.toDouble()))
    }
  }

  companion object {
    const val FIELD_RECENT_DATA = "field_recent_data"
    const val FIELD_HISTORY = "field_history"

  }

  fun saveBitcoinHistory(history: List<Bitcoin>): Completable {
    return Completable.fromCallable {
      val json = bitcoinConverter.convertListToJson(history)
      sharedPreferences
          .edit()
          .putString(FIELD_HISTORY, json)
          .apply()
    }
  }

  fun getHistory(): Single<List<Bitcoin>> {
    return Single.create<List<Bitcoin>> { emitter ->
      val historyJson = sharedPreferences.getString(FIELD_HISTORY, "")
      try {
        emitter.onSuccess(bitcoinConverter.convertJsonToList(historyJson))
      } catch (exception: Exception) {
        emitter.onError(Exception("No cached data"))
      }
    }
  }
}

