package com.srb9181.currencyconverter.util

import com.srb9181.currencyconverter.util.FunctionUtils.getNumberInText
import org.junit.Assert.*
import org.junit.Test

class FunctionUtilsTest {

    @Test
    fun isTimeToRefreshFalse_Test() {
        val time=FunctionUtils.isTimeToRefresh(System.currentTimeMillis()-(1*30*60*1000),Constants.API_CALL_OFFSET)
        assertFalse(time)
    }


    @Test
    fun isTimeToRefreshTrue_Test() {
        val time=FunctionUtils.isTimeToRefresh(System.currentTimeMillis()-(2*60*60*1000),Constants.API_CALL_OFFSET)
        assertTrue(time)
    }

    @Test
    fun getLocalCurrencyEquals_Test() {
        val localCurrency=FunctionUtils.getLocalCurrency()
        assertEquals("INR",localCurrency)
    }

    @Test
    fun getLocalCurrencyNotEquals_Test() {
        val localCurrency=FunctionUtils.getLocalCurrency()
        assertNotEquals("USD",localCurrency)
    }

    @Test
    fun getScaleTrueWhenNoDecimalPoints_Test() {
        val scale = FunctionUtils.getScale(12.toBigDecimal())
        assertEquals(scale,2)
    }

    @Test
    fun getScaleTrueWhenDecimalPoints_Test() {
        val scale = FunctionUtils.getScale(12.233232.toBigDecimal())
        assertEquals(scale,2)
    }

    @Test
    fun getScaleTrueWhenDecimalPointsHavingZero_Test() {
        val scale = FunctionUtils.getScale(12.0000024550.toBigDecimal())
        assertEquals(scale,7)
    }


    @Test
    fun getNumberInText_Test() {
        val result = 443.234242.toBigDecimal().getNumberInText()
        assertEquals("443.23",result)
    }


    @Test
    fun getNumberInTextDecimalPointsHavingZero_Test() {
        val result = 443.toBigDecimal().getNumberInText()
        assertEquals("443.00",result)
    }
}