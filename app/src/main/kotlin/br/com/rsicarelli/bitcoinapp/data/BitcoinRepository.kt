package br.com.rsicarelli.bitcoinapp.data

import br.com.rsicarelli.bitcoinapp.data.model.Bitcoin
import br.com.rsicarelli.bitcoinapp.data.responses.BitcoinHistoryResponse
import br.com.rsicarelli.bitcoinapp.data.responses.RealtimeBitcoinResponse
import io.reactivex.Completable
import io.reactivex.Single

interface BitcoinRepository {
  fun realtimeData(): Single<RealtimeBitcoinResponse>
  fun getPriceInterval(start: String, end: String): Single<BitcoinHistoryResponse>
  fun getLastRealtimeData(): Single<Bitcoin>
  fun saveLastRealtimeData(bitcoin: Bitcoin): Completable
  fun saveLastHistory(history: List<Bitcoin>): Completable
  fun getLastHistory(): Single<List<Bitcoin>>
}