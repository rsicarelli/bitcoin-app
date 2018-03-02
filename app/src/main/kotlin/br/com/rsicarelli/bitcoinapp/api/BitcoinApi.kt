package br.com.rsicarelli.bitcoinapp.api

import br.com.rsicarelli.bitcoinapp.BuildConfig
import br.com.rsicarelli.bitcoinapp.data.responses.BitcoinHistoryResponse
import br.com.rsicarelli.bitcoinapp.data.responses.RealtimeBitcoinResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface BitcoinApi {
  @GET("/v1/bpi/currentprice.json")
  fun getCurrentPrice(): Single<RealtimeBitcoinResponse>

  @GET("v1/bpi/historical/close.json")
  fun getPriceInterval(
      @Query("start") start: String?,
      @Query("end") end: String
  ): Single<BitcoinHistoryResponse>

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

