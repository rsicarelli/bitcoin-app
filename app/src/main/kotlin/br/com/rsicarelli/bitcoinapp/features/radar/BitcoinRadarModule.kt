package br.com.rsicarelli.bitcoinapp.features.radar

import android.content.Context
import br.com.rsicarelli.bitcoinapp.api.BitcoinApi
import br.com.rsicarelli.bitcoinapp.data.BitcoinRepository
import br.com.rsicarelli.bitcoinapp.data.BitcoinRepositoryImpl
import br.com.rsicarelli.bitcoinapp.data.DateRangeCreator
import br.com.rsicarelli.bitcoinapp.data.persistance.BitcoinConverter
import br.com.rsicarelli.bitcoinapp.data.persistance.BitcoinSharedPreferences
import br.com.rsicarelli.bitcoinapp.di.module.SchedulersComposer
import com.google.gson.Gson
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
  fun providesBitcoinConverter(): BitcoinConverter {
    return BitcoinConverter(Gson())
  }

  @Provides
  fun providesBitcoinSharedPreferences(context: Context, bitcoinConverter: BitcoinConverter): BitcoinSharedPreferences {
    val sharedPreferences = context.getSharedPreferences("bitcoin_prefs", Context.MODE_PRIVATE)
    return BitcoinSharedPreferences(sharedPreferences, bitcoinConverter)
  }

  @Provides
  fun providesBitcoinRepository(bitcoinSharedPreferences: BitcoinSharedPreferences): BitcoinRepository {
    return BitcoinRepositoryImpl(BitcoinApi.create(), bitcoinSharedPreferences)
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
