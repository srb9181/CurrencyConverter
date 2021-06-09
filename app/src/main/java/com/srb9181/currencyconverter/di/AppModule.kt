package com.srb9181.currencyconverter.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.srb9181.currencyconverter.data.local.CurrencyConverterDao
import com.srb9181.currencyconverter.data.local.CurrencyConverterDatabase
import com.srb9181.currencyconverter.data.remote.ApiEndPoints
import com.srb9181.currencyconverter.repository.CurrencyConverterRepoImpl
import com.srb9181.currencyconverter.repository.CurrencyConverterRepository
import com.srb9181.currencyconverter.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context): CurrencyConverterDatabase {
        return Room.databaseBuilder(
            appContext,
            CurrencyConverterDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .enableMultiInstanceInvalidation()
            .build()
    }

    @Provides
    fun provideCurrencyDao(database: CurrencyConverterDatabase):CurrencyConverterDao{
        return database.currencyConverterDao()
    }


    @Provides
    fun providePreferences(@ApplicationContext appContext: Context) :SharedPreferences{
        return  appContext.getSharedPreferences(Constants.PREFERENCE_NAME,Context.MODE_PRIVATE)
    }

    @Provides
    fun provideHTTPLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor;
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideCurrencyConverterApi(okHttpClient: OkHttpClient):ApiEndPoints {

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build().create(ApiEndPoints::class.java)
    }


    @Provides
    fun provideCurrencyConverterRepoImpl(dao: CurrencyConverterDao, api: ApiEndPoints): CurrencyConverterRepository {
        return CurrencyConverterRepoImpl(dao, api) as CurrencyConverterRepository
    }

}