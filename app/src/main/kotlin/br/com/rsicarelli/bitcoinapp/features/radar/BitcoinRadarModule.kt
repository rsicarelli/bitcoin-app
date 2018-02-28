package br.com.rsicarelli.bitcoinapp.features.radar

import br.com.rsicarelli.bitcoinapp.api.BitcoinApi
import br.com.rsicarelli.bitcoinapp.data.BitcoinRepository
import br.com.rsicarelli.bitcoinapp.data.BitcoinRepositoryImpl
import br.com.rsicarelli.bitcoinapp.data.DateRangeCreator
import br.com.rsicarelli.bitcoinapp.di.module.SchedulersComposer
import dagger.Module
import dagger.Provides
import java.util.Calendar

@Module
class BitcoinRadarModule {

  @Provides
  fun providesBitcoinRadarActivity(
      activity: BitcoinRadarActivity
  ): BitcoinRadarContract.View = activity

  @Provides
  fun providesBitcoinRepository(): BitcoinRepository {
    return BitcoinRepositoryImpl(BitcoinApi.create())
  }

  @Provides
  fun providesBitcoinRadarPresenter(
      activity: BitcoinRadarActivity,
      bitcoinRepository: BitcoinRepository,
      schedulersComposer: SchedulersComposer,
      dateRangeCreator: DateRangeCreator
  ): BitcoinRadarContract.Presenter {
    return BitcoinRadarPresenter(activity, bitcoinRepository, schedulersComposer, dateRangeCreator)
  }

  @Provides
  fun providesDateRangeCreator() = DateRangeCreator(Calendar.getInstance())
}
