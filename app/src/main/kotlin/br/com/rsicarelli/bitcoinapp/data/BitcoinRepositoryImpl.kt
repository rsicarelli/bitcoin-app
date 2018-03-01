package br.com.rsicarelli.bitcoinapp.data

import br.com.rsicarelli.bitcoinapp.api.BitcoinApi
import br.com.rsicarelli.bitcoinapp.api.BitcoinHistoryResponse
import br.com.rsicarelli.bitcoinapp.data.persistance.BitcoinSharedPreferences
import io.reactivex.Completable
import io.reactivex.Single

class BitcoinRepositoryImpl(
    private val bitcoinApi: BitcoinApi,
    private val bitcoinSharedPreferences: BitcoinSharedPreferences
) : BitcoinRepository {

  override fun realtimeData(): Single<RealtimeBitcoinResponse> {
    return bitcoinApi.getCurrentPrice()
  }

  override fun getPriceInterval(start: String, end: String): Single<BitcoinHistoryResponse> {
    return bitcoinApi.getPriceInterval(start, end)
  }

  override fun getLastRealtimeData(): Single<Bitcoin> {
    return bitcoinSharedPreferences.getLastBitcoinData()
  }

  override fun saveLastRealtimeData(bitcoin: Bitcoin): Completable {
    return bitcoinSharedPreferences.saveBitcoinData(bitcoin)
  }

  override fun saveLastHistory(history: List<Bitcoin>): Completable {
    return bitcoinSharedPreferences.saveBitcoinHistory(history)
  }

  override fun getLastHistory(): Single<List<Bitcoin>> {
    return bitcoinSharedPreferences.getHistory()
  }
}

interface BitcoinRepository {
  fun realtimeData(): Single<RealtimeBitcoinResponse>
  fun getPriceInterval(start: String, end: String): Single<BitcoinHistoryResponse>
  fun getLastRealtimeData(): Single<Bitcoin>
  fun saveLastRealtimeData(bitcoin: Bitcoin): Completable
  fun saveLastHistory(history: List<Bitcoin>): Completable
  fun getLastHistory(): Single<List<Bitcoin>>
}
