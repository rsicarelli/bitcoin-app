package br.com.rsicarelli.bitcoinapp.api

import br.com.rsicarelli.bitcoinapp.BuildConfig
import br.com.rsicarelli.bitcoinapp.data.RealtimeBitcoinResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface BitcoinApi {
  @GET("/v1/bpi/currentprice.json")
  fun getCurrentPrice(): Single<RealtimeBitcoinResponse>

  @GET("v1/bpi/historical/close.json?start=2018-02-10&end=2018-02-27")
  fun getPriceInterval(): Single<BitcoinHistoryResponse>

  companion object {
    private const val URL = "https://api.coindesk.com"

    fun create(): BitcoinApi {
      val httpLoggingInterceptor = HttpLoggingInterceptor()

      httpLoggingInterceptor.level = when {
        BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
        else -> HttpLoggingInterceptor.Level.NONE
      }

      val client = OkHttpClient.Builder()
          .addInterceptor(httpLoggingInterceptor)
          .build()

      val retrofit = Retrofit.Builder()
          .baseUrl(URL)
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .addConverterFactory(GsonConverterFactory.create())
          .client(client)
          .build()

      return retrofit.create(BitcoinApi::class.java)
    }
  }
}

data class BitcoinHistoryResponse(
    val bpi: Map<String, Double>
)
