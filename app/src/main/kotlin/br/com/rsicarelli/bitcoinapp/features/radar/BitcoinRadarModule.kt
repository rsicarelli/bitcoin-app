package br.com.rsicarelli.bitcoinapp.features.radar

import dagger.Module
import dagger.Provides

@Module
class BitcoinRadarModule {

  @Provides
  fun providesBitcoinRadarActivity(
      activity: BitcoinRadarActivity
  ): BitcoinRadarContract.View = activity

  @Provides
  fun providesBitcoinRadarPresenter(
      activity: BitcoinRadarActivity
  ): BitcoinRadarContract.Presenter {
    return BitcoinRadarPresenter(activity)
  }
}
