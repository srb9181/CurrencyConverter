package com.srb9181.currencyconverter.repository

import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import com.srb9181.currencyconverter.data.local.CurrencyConverterDao
import com.srb9181.currencyconverter.data.remote.ApiEndPoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CurrencyConverterRepoImpl @Inject constructor(
    private val currencyConverterDao: CurrencyConverterDao,
    private val apiEndPoints: ApiEndPoints): CurrencyConverterRepository{

    override suspend fun getAllCurrenciesFromApi(key:String): Flow<Long> {
        return flow {
            try {

                val result =apiEndPoints.getLiveCurrenciesConversions(key)
                if (result.success){
                    val localData =  mutableListOf<CurrencyConversionRates>()

                    for(currency in result.quotes){
                        result.source
                        localData.add( CurrencyConversionRates(currency.key.hashCode(),currency.key.removePrefix(result.source),currency.value,result.source,1.0,result.timestamp))
                    }
                    currencyConverterDao.addOrUpdate(localData)
                    emit(result.timestamp)
                }
                else
                    throw Throwable(result.error?.info)
            }
            catch (e:Exception){
                throw e
            }
        }.flowOn(Dispatchers.IO)
    }


    override fun getAllCurrencies(): Flow<List<CurrencyConversionRates>> {
        return currencyConverterDao.getAllCurrency()
            .distinctUntilChanged()
            .flowOn(Dispatchers.IO)
    }
}