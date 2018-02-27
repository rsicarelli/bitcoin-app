package br.com.rsicarelli.bitcoinapp

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import kotlinx.android.synthetic.main.view_bitcoin_detail.view.bitcoinCurrency
import kotlinx.android.synthetic.main.view_bitcoin_detail.view.bitcoinDate
import kotlinx.android.synthetic.main.view_bitcoin_detail.view.price
import java.text.NumberFormat

class BitcoinDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  init {
    inflate(context, R.layout.view_bitcoin_detail, this)
  }

  fun bind(bitcoin: Bitcoin) {
    with(bitcoin) {
      price.text = NumberFormat.getCurrencyInstance().format(value)
      date?.let {
        bitcoinDate.visibility = View.VISIBLE
        bitcoinDate.text = it
      }
      bitcoinCurrency.text = currency.name
    }
  }


}