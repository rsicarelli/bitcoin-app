package br.com.rsicarelli.bitcoinapp.extensions

import android.view.View

fun View.show() {
  this.visibility = View.VISIBLE
}

fun View.gone() {
  this.visibility = View.GONE
}