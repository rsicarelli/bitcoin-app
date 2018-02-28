package br.com.rsicarelli.bitcoinapp.di.module

import br.com.rsicarelli.bitcoinapp.di.ActivityScoped
import br.com.rsicarelli.bitcoinapp.features.radar.BitcoinRadarActivity
import br.com.rsicarelli.bitcoinapp.features.radar.BitcoinRadarModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

  @ActivityScoped
  @ContributesAndroidInjector(modules = [(BitcoinRadarModule::class)])
  internal abstract fun bitcoinRadarActivity(): BitcoinRadarActivity

}
