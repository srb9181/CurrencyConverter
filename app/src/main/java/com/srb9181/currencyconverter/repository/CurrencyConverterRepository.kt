package com.srb9181.currencyconverter.repository

import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import kotlinx.coroutines.flow.Flow

interface CurrencyConverterRepository {

    suspend fun getAllCurrenciesFromApi(key:String):Flow<Long>

    fun getAllCurrencies():Flow<List<CurrencyConversionRates>>

}