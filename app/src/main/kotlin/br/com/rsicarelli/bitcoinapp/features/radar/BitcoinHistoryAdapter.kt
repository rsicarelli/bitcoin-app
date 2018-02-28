package br.com.rsicarelli.bitcoinapp.features.radar

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.rsicarelli.bitcoinapp.R
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BitcoinHistoryAdapter : RecyclerView.Adapter<BitcoinHistoryAdapter.BitcoinViewHolder>() {

  private val bitcoinHistory = arrayListOf<Bitcoin>()

  fun addHistory(users: List<Bitcoin>) {
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

    private val value by lazy { view.findViewById<TextView>(R.id.price) }
    private val currency by lazy { view.findViewById<TextView>(R.id.bitcoinCurrency) }
    private val date by lazy { view.findViewById<TextView>(R.id.bitcoinDate) }

    fun bind(bitcoin: Bitcoin) {
      value.text = NumberFormat.getCurrencyInstance().format(bitcoin.value)
      currency.text = bitcoin.currency.name
      date.text = bitcoin.date?.prettyDate()
      date.visibility = View.VISIBLE
    }
  }

  fun String.prettyDate(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val date = simpleDateFormat.parse(this)

    val calendar = Calendar.getInstance().apply {
      time = date
    }

    with(calendar) {
      val day = get(Calendar.DAY_OF_MONTH)
      val month = getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault())

      return "${month.toUpperCase()} $day"
    }
  }
}
