package br.com.rsicarelli.bitcoinapp.features.radar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.rsicarelli.bitcoinapp.R
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import br.com.rsicarelli.bitcoinapp.extensions.prettyDate
import br.com.rsicarelli.bitcoinapp.extensions.show
import java.text.NumberFormat

class BitcoinHistoryAdapter : RecyclerView.Adapter<BitcoinHistoryAdapter.BitcoinViewHolder>() {

  private val bitcoinHistory = arrayListOf<Bitcoin>()

  fun addHistory(users: List<Bitcoin>) {
    this.bitcoinHistory.clear()
    this.bitcoinHistory.addAll(users)
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitcoinViewHolder {
    val placeView = LayoutInflater.from(parent.context)
        .inflate(R.layout.view_bitcoin_detail, parent, false)

    return BitcoinViewHolder(placeView)
  }

  override fun onBindViewHolder(viewHolder: BitcoinViewHolder, position: Int) {
    val bitcoin = bitcoinHistory[position]
    viewHolder.bind(bitcoin)
  }

  override fun getItemCount(): Int = bitcoinHistory.size

  inner class BitcoinViewHolder(
      private val view: View
  ) : RecyclerView.ViewHolder(view) {

    fun bind(bitcoin: Bitcoin) {
      val value = view.findViewById<TextView>(R.id.price)
      val date = view.findViewById<TextView>(R.id.bitcoinDate)

      value.text = NumberFormat.getCurrencyInstance().format(bitcoin.value)
      bitcoin.date?.let {
        date.text = it.prettyDate()
        date.show()
      }
    }
  }
}

