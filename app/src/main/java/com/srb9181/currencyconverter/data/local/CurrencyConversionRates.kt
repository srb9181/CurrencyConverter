package com.srb9181.currencyconverter.data.local

import android.icu.util.CurrencyAmount
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.util.*

@Entity(tableName = "currency_rates")
data class CurrencyConversionRates(
    @PrimaryKey(autoGenerate = false)
    val _id:Int,
    var currencyCode:String,
    var currencyRate:Double,
    var currencySource: String,
    var currencySourceRate: Double,
    var timeStamp: Long
)