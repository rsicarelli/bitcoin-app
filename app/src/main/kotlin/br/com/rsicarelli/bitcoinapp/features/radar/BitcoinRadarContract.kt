package br.com.rsicarelli.bitcoinapp.features.radar

import br.com.rsicarelli.bitcoinapp.data.model.Bitcoin

interface BitcoinRadarContract {

  interface View {
    fun bindRealtimeData(bitcoin: Bitcoin)
    fun bindHistory(bitcoinHistory: List<Bitcoin>)
  }

  interface Presenter {
    fun onCreate()
    fun onResume()
    fun onDestroy()
    fun onPause()
  }
}
