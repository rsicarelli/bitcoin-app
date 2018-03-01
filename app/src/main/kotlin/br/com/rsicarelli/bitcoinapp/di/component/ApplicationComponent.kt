package br.com.rsicarelli.teamworkchallenge.di.component

import br.com.rsicarelli.bitcoinapp.di.module.ActivityBuilderModule
import br.com.rsicarelli.bitcoinapp.di.module.SchedulersModule
import br.com.rsicarelli.bitcoinapp.global.ApplicationModule
import br.com.rsicarelli.bitcoinapp.global.BitcoinRadarApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
      (AndroidSupportInjectionModule::class),
      (ActivityBuilderModule::class),
      (SchedulersModule::class),
      (ApplicationModule::class)
    ]
)
interface ApplicationComponent : AndroidInjector<BitcoinRadarApplication> {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun application(application: BitcoinRadarApplication): Builder

    fun build(): ApplicationComponent
  }
}
