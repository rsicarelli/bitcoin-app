package br.com.rsicarelli.bitcoinapp.data

import br.com.rsicarelli.bitcoinapp.api.BitcoinApi
import br.com.rsicarelli.bitcoinapp.api.BitcoinHistoryResponse
import io.reactivex.Single

class BitcoinRepositoryImpl(
    private val bitcoinApi: BitcoinApi
) : BitcoinRepository {

  override fun realtimeData(): Single<RealtimeBitcoinResponse> {
    return bitcoinApi.getCurrentPrice()
  }

  override fun getPriceInterval(start: String, end: String): Single<BitcoinHistoryResponse> {
    return bitcoinApi.getPriceInterval(start, end)
  }


}

interface BitcoinRepository {
  fun realtimeData(): Single<RealtimeBitcoinResponse>
  fun getPriceInterval(start: String, end: String): Single<BitcoinHistoryResponse>
}