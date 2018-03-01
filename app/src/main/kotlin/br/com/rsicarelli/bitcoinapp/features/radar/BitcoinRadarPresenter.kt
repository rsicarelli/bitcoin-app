package br.com.rsicarelli.bitcoinapp.features.radar

import android.os.Bundle
import android.util.Log
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import br.com.rsicarelli.bitcoinapp.data.BitcoinRepository
import br.com.rsicarelli.bitcoinapp.data.DateRangeCreator
import br.com.rsicarelli.bitcoinapp.di.module.SchedulersComposer
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class BitcoinRadarPresenter(
    private val view: BitcoinRadarContract.View,
    private val bitcoinRepository: BitcoinRepository,
    private val schedulersComposer: SchedulersComposer,
    private val dateRangeCreator: DateRangeCreator
) : BitcoinRadarContract.Presenter {

  private val disposable = CompositeDisposable()

  operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
  }

  override fun onCreate(savedInstance: Bundle?) {
    getCachedData()

    fetchBitcoinHistory()
  }

  private fun getCachedData() {
    bitcoinRepository.getLastRealtimeData()
        .subscribeOn(schedulersComposer.executorScheduler())
        .observeOn(schedulersComposer.mainThreadScheduler())
        .subscribe({
          view.bindRealtimeData(it)
          Log.d("BitconRadar", "Got cached data: $it")
        }, {
          Log.e("BitcoinRadar", it.message)
        })

    bitcoinRepository.getLastHistory()
        .subscribeOn(schedulersComposer.executorScheduler())
        .observeOn(schedulersComposer.mainThreadScheduler())
        .subscribe({
          view.bindHistory(it)
        }, {
          Log.e("BitcoinRadar", it.message)
        })
  }

  private fun fetchBitcoinHistory() {
    val dateRangeFromNow = dateRangeCreator.getDateRangeFromNow(-15)

    disposable += bitcoinRepository.getPriceInterval(dateRangeFromNow.first, dateRangeFromNow.second)
        .subscribeOn(schedulersComposer.executorScheduler())
        .observeOn(schedulersComposer.mainThreadScheduler())
        .flatMapObservable { Observable.fromIterable(it.bpi.entries) }
        .map { (date, value) ->
          Bitcoin(value = value, date = date)
        }
        .toSortedList({ current, previous ->
          previous.getDateTime().compareTo(current.getDateTime())
        })
        .doOnSuccess { view.bindHistory(it) }
        .flatMapCompletable { bitcoinRepository.saveLastHistory(it) }
        .onErrorComplete()
        .subscribe()
  }


  override fun onResume() {
    disposable += bitcoinRepository.realtimeData()
        .subscribeOn(schedulersComposer.executorScheduler())
        .observeOn(schedulersComposer.mainThreadScheduler())
        .map {
          val usd = it.bpi.usd
          Bitcoin(
              value = usd.rate_float,
              date = null
          )
        }
        .doOnSuccess {
          view.bindRealtimeData(it)
        }.flatMapCompletable {
          bitcoinRepository.saveLastRealtimeData(it)
              .subscribeOn(schedulersComposer.executorScheduler())
        }
        .retryWhen { it.delay(5, TimeUnit.SECONDS) }
        .repeatWhen { it.delay(1, TimeUnit.MINUTES) }
        .doOnError { Log.e("BitcoinRadar", it.message) }
        .onErrorComplete()
        .subscribe()
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun onDestroy() {
    disposable.clear()
  }
}
