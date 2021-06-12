package com.srb9181.currencyconverter.repository

import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import kotlinx.coroutines.flow.Flow

class CurrencyConverterRepoImplTest : CurrencyConverterRepository {


    override suspend fun getAllCurrenciesFromApi(key: String): Flow<Long> {
        TODO("Not yet implemented")
    }

    override fun getAllCurrencies(): Flow<List<CurrencyConversionRates>> {
        TODO("Not yet implemented")
    }

}