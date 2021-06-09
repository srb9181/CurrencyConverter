package com.srb9181.currencyconverter.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class CurrencyConverterTest {

    private lateinit var currencyConverterDatabase: CurrencyConverterDatabase
    private lateinit var currencyConverterDao: CurrencyConverterDao

    @Before
    fun setUp() {
        currencyConverterDatabase= Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),CurrencyConverterDatabase::class.java)
            .allowMainThreadQueries().build()
        currencyConverterDao= currencyConverterDatabase.currencyConverterDao()
    }

    @Test
    fun insertCurrencies() = runBlocking {
         val data1=CurrencyConversionRates(1,"INR",73.5454,"USD",1.0,System.currentTimeMillis())
         val result=currencyConverterDao.insertCurrency(mutableListOf(data1))

         assertTrue(result.isNotEmpty())
       // val data=currencyConverterDao.getAllCurrency().distinctUntilChanged().first()
    }

    @Test
    fun addOrUpdateCurrency() = runBlocking {
        val data1=CurrencyConversionRates(1,"INR",73.5454,"USD",1.0,System.currentTimeMillis())
         currencyConverterDao.addOrUpdate(mutableListOf(data1))
        val data=currencyConverterDao.getAllCurrency().distinctUntilChanged().first()
        print(data.size)
        assertTrue(data.isNotEmpty())
    }

    @Test
    fun deleteCurrency() = runBlocking {
        val data1=CurrencyConversionRates(1,"INR",73.5454,"USD",1.0,System.currentTimeMillis())
        currencyConverterDao.addOrUpdate(mutableListOf(data1))
        currencyConverterDao.deleteCurrency(data1)
        val data=currencyConverterDao.getAllCurrency().distinctUntilChanged().first()
        println(data.size)
        assertFalse(data.isNotEmpty())
    }





    @After
    fun tearDown() {
        currencyConverterDatabase.close()
    }
}