package com.srb9181.currencyconverter.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyConversionRates(
    @PrimaryKey(autoGenerate = false)
    val _id:Int,
    val currencyCode:String,
    val currencyRate:Double,
    val currencySource: String,
    val currencySourceRate: Double,
    val timeStamp: Long
)