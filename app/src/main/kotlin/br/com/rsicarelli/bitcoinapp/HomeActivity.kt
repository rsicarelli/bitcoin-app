package br.com.rsicarelli.bitcoinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.com.rsicarelli.bitcoinapp.api.BitcoinApi
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import br.com.rsicarelli.bitcoinapp.data.Currency
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_home.bitcoinDetailView
import kotlinx.android.synthetic.main.activity_home.recyclerView
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

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
            bitcoinDetailView.bind(it)
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
          val bitcoinHistoryAdapter = BitcoinHistoryAdapter()
          bitcoinHistoryAdapter.addUsers(it)
          recyclerView.adapter = bitcoinHistoryAdapter
          Log.d("memes", it.toString())
        }, {
          Log.e("memes", it.message)
        })
  }
}
