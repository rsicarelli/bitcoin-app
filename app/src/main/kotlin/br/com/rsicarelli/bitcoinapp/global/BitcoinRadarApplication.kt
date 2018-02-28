package br.com.rsicarelli.bitcoinapp.global

import br.com.rsicarelli.teamworkchallenge.di.component.ApplicationComponent
import br.com.rsicarelli.teamworkchallenge.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class BitcoinRadarApplication : DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    applicationComponent = DaggerApplicationComponent.builder()
        .application(this)
        .build()

    applicationComponent.inject(this)

    return applicationComponent
  }

  companion object {
    lateinit var applicationComponent: ApplicationComponent
  }
}