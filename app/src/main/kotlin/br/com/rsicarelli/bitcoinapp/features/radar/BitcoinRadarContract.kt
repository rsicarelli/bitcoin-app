package br.com.rsicarelli.bitcoinapp.features.radar

import android.os.Bundle
import br.com.rsicarelli.bitcoinapp.data.Bitcoin

interface BitcoinRadarContract {

  interface View {
    fun bindRealtimeData(bitcoin: Bitcoin)
    fun bindHistory(bitcoinHistory: List<Bitcoin>)
  }

  interface Presenter {
    fun onCreate(savedInstance: Bundle?)
    fun onResume()
    fun onSaveInstanceState(outState: Bundle?)
    fun onDestroy()
  }
}
