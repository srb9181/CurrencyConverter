package com.srb9181.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CurrencyConversionRates::class],version = 1,exportSchema = false)
abstract class CurrencyConverterDatabase:RoomDatabase() {

    abstract fun currencyConverterDao(): CurrencyConverterDao
}