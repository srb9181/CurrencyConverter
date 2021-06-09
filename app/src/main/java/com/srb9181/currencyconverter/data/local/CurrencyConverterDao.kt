package com.srb9181.currencyconverter.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface CurrencyConverterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCurrency(currencies:List<CurrencyConversionRates>):List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCurrency(currencies:List<CurrencyConversionRates>)

    @Delete
    suspend fun deleteCurrency(currencyConversionRates: CurrencyConversionRates)

    @Query("SELECT * FROM currency_rates")
    fun getAllCurrency():Flow<List<CurrencyConversionRates>>

    @Query("SELECT timeStamp FROM currency_rates limit 1")
    fun getTimeStamp():Flow<Long>

    @Transaction
    suspend fun addOrUpdate(currencies:List<CurrencyConversionRates>){
        val result = insertCurrency(currencies)
        val updateList = result.withIndex().filter { it.value==-1L }.map { currencies[it.index] }
        if(updateList.isNotEmpty())updateCurrency(updateList)
    }

}