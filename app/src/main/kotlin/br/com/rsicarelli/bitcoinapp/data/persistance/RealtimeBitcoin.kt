package br.com.rsicarelli.bitcoinapp.data.persistance

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Database
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Query
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Update
import android.content.Context
import android.support.annotation.NonNull
import br.com.rsicarelli.bitcoinapp.data.Bitcoin
import io.reactivex.Single
import java.util.UUID


@Entity(tableName = "lastdata")
data class RealtimeBitcoin(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    val id: String = UUID.randomUUID().toString(),
    @Embedded
    val bitcoin: Bitcoin
)

@Dao
interface BitcoinDao {

  @Query("SELECT * FROM lastdata")
  fun getRecentBitcoinData(): Single<RealtimeBitcoin>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertRecentBitcoinData(realtimeBitcoin: RealtimeBitcoin)

  @Update(onConflict = OnConflictStrategy.REPLACE)
  fun updateRecentBitcoinData(realtimeBitcoin: RealtimeBitcoin)
}

@Database(entities = [(RealtimeBitcoin::class)], version = 1)
abstract class BitcoinDatabase : RoomDatabase() {

  abstract fun bitcoinDao(): BitcoinDao

  companion object {

    @Volatile
    private var INSTANCE: BitcoinDatabase? = null

    fun getInstance(context: Context): BitcoinDatabase =
        INSTANCE ?: synchronized(this) {
          INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

    private fun buildDatabase(context: Context) =
        Room.databaseBuilder(context.applicationContext,
            BitcoinDatabase::class.java, "BitcoinApp.db")
            .build()
  }
}
