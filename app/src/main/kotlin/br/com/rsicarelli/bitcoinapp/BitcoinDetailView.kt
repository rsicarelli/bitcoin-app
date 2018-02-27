package br.com.rsicarelli.bitcoinapp

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet

class BitcoinDetailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

  init {
    inflate(context, R.layout.view_bitcoin_detail, this)
  }



}
