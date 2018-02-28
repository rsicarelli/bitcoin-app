package br.com.rsicarelli.bitcoinapp.global

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {

  @Provides
  fun provideContext(application: BitcoinRadarApplication): Context = application

}
