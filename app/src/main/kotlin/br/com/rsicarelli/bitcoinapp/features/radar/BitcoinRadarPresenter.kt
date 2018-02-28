package br.com.rsicarelli.bitcoinapp.features.radar

import android.os.Bundle
import android.util.Log
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import br.com.rsicarelli.bitcoinapp.data.BitcoinRepository
import br.com.rsicarelli.bitcoinapp.data.Currency
import br.com.rsicarelli.bitcoinapp.data.DateRangeCreator
import br.com.rsicarelli.bitcoinapp.di.module.SchedulersComposer
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class BitcoinRadarPresenter(
    private val view: BitcoinRadarContract.View,
    private val bitcoinRepository: BitcoinRepository,
    private val schedulersComposer: SchedulersComposer,
    private val dateRangeCreator: DateRangeCreator
) : BitcoinRadarContract.Presenter {
  override fun onCreate(savedInstance: Bundle?) {

    val dateRangeFromNow = dateRangeCreator.getDateRangeFromNow(-15)

    bitcoinRepository.getPriceInterval(dateRangeFromNow.first, dateRangeFromNow.second)
        .subscribeOn(schedulersComposer.executorScheduler())
        .observeOn(schedulersComposer.mainThreadScheduler())
        .flatMapObservable { Observable.fromIterable(it.bpi.entries) }
        .map { (date, value) ->
          Bitcoin(value = value, date = date, currency = Currency.USD)
        }
        .toSortedList({ current, previous ->
          previous.getDateTime().compareTo(current.getDateTime())
        })
        .subscribe({
          view.bindHistory(it)
          Log.e("BitcoinRadar", "Succcess -> $it")
        }, {
          Log.e("BitcoinRadar", it.message)
        })
  }

  override fun onResume() {
    Observable.fromCallable<Any> {
      bitcoinRepository.realtimeData()
          .subscribeOn(schedulersComposer.executorScheduler())
          .observeOn(schedulersComposer.mainThreadScheduler())
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
            Log.e("BitcoinRadar", it.message)
          })
    }.repeatWhen {
          it.concatMap<Any> {
            Observable.timer(1, TimeUnit.MINUTES)
          }
        }.subscribe()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onDestroy() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}