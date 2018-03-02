package br.com.rsicarelli.bitcoinapp.features.radar

import android.os.Bundle
import br.com.rsicarelli.bitcoinapp.R
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import br.com.rsicarelli.bitcoinapp.extensions.gone
import br.com.rsicarelli.bitcoinapp.extensions.show
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.bitcoinDetailView
import kotlinx.android.synthetic.main.activity_home.fetchingData
import kotlinx.android.synthetic.main.activity_home.historyTitle
import kotlinx.android.synthetic.main.activity_home.recyclerView
import javax.inject.Inject

class BitcoinRadarActivity : DaggerAppCompatActivity(),
    BitcoinRadarContract.View {

  @Inject
  lateinit var presenter: BitcoinRadarContract.Presenter

  private val bitcoinHistoryAdapter by lazy { BitcoinHistoryAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    presenter.onCreate()
  }

  override fun onResume() {
    super.onResume()
    presenter.onResume()
  }

  override fun onPause() {
    super.onPause()
    presenter.onPause()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter.onDestroy()
  }

  override fun bindRealtimeData(bitcoin: Bitcoin) {
    bitcoinDetailView.bind(bitcoin)
    fetchingData.gone()
    bitcoinDetailView.show()
  }

  override fun bindHistory(bitcoinHistory: List<Bitcoin>) {
    bitcoinHistoryAdapter.addHistory(bitcoinHistory)
    recyclerView.adapter = bitcoinHistoryAdapter
    historyTitle.show()
    recyclerView.show()
  }
}
