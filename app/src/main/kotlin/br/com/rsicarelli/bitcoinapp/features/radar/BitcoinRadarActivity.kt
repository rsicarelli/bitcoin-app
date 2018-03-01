package br.com.rsicarelli.bitcoinapp.features.radar

import android.os.Bundle
import br.com.rsicarelli.bitcoinapp.R
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.bitcoinDetailView
import kotlinx.android.synthetic.main.activity_home.recyclerView
import javax.inject.Inject

class BitcoinRadarActivity : DaggerAppCompatActivity(),
    BitcoinRadarContract.View {

  @Inject
  lateinit var presenter: BitcoinRadarContract.Presenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    presenter.onCreate(savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun bindRealtimeData(bitcoin: Bitcoin) {
    bitcoinDetailView.bind(bitcoin)
  }

  override fun bindHistory(bitcoinHistory: List<Bitcoin>) {
    val bitcoinHistoryAdapter = BitcoinHistoryAdapter()
    bitcoinHistoryAdapter.addHistory(bitcoinHistory)
    recyclerView.adapter = bitcoinHistoryAdapter
  }
}
