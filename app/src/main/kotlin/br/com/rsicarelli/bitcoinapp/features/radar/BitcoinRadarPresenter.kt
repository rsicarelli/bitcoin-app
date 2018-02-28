package br.com.rsicarelli.bitcoinapp.features.radar

import android.os.Bundle
import android.util.Log
import br.com.rsicarelli.bitcoinapp.R.id.bitcoinDetailView
import br.com.rsicarelli.bitcoinapp.R.id.recyclerView
import br.com.rsicarelli.bitcoinapp.api.BitcoinApi
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import br.com.rsicarelli.bitcoinapp.data.Currency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.bitcoinDetailView
import kotlinx.android.synthetic.main.activity_home.recyclerView
import java.util.concurrent.TimeUnit

class BitcoinRadarPresenter(
    private val view: BitcoinRadarContract.View
) : BitcoinRadarContract.Presenter {
  override fun onCreate(savedInstance: Bundle?) {
    val create = BitcoinApi.create()

    Observable.fromCallable<Any> {
      create.getCurrentPrice()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .map {
            val usd = it.bpi.usd
            Bitcoin(
                value = usd.rate_float,
                date = null,
                currency = Currency.fromString(usd.code)
            )
          }.subscribe({
            view.bindRealtimeData(it)
          }, {
            Log.e("memes", "memes")
          })
    }.repeatWhen {
          it.concatMap<Any> {
            Observable.timer(1, TimeUnit.MINUTES)
          }
        }.subscribe()

    create.getPriceInterval().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        .flatMapObservable { Observable.fromIterable(it.bpi.entries) }
        .map { (date, value) ->
          Bitcoin(value = value, date = date, currency = Currency.USD)
        }
        .toList()
        .subscribe({
          view.bindHistory(it)
          Log.d("memes", it.toString())
        }, {
          Log.e("memes", it.message)
        })
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onDestroy() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}