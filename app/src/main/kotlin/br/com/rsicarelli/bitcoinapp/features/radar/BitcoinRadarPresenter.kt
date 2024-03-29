package br.com.rsicarelli.bitcoinapp.features.radar

import android.util.Log
import br.com.rsicarelli.bitcoinapp.data.model.Bitcoin
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

  override fun onCreate() {
    getCachedData()

    fetchBitcoinHistory()
  }

  private fun getCachedData() {
    bitcoinRepository.getLastRealtimeData()
        .subscribeOn(schedulersComposer.executorScheduler())
        .observeOn(schedulersComposer.mainThreadScheduler())
        .subscribe({
          view.bindRealtimeData(it)
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
        .retryWhen { it.delay(5, TimeUnit.SECONDS) }
        .flatMapObservable { Observable.fromIterable(it.history.entries) }
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
        .map { Bitcoin(value = it.historyInfo.getPrice()) }
        .doOnSuccess {
          view.bindRealtimeData(it)
        }
        .flatMapCompletable { bitcoinRepository.saveLastRealtimeData(it) }
        .retryWhen { it.delay(5, TimeUnit.SECONDS) }
        .repeatWhen { it.delay(1, TimeUnit.MINUTES) }
        .doOnError { Log.e("BitcoinRadar", it.message) }
        .onErrorComplete()
        .subscribe()
  }

  override fun onDestroy() {
    disposable.clear()
  }

  override fun onPause() {
    disposable.clear()
  }

  operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
  }
}
