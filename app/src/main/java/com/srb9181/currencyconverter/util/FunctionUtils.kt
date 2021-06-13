package com.srb9181.currencyconverter.util

import android.content.Context
import android.widget.Toast
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.math.log10

object FunctionUtils {

    fun isTimeToRefresh(patientRefreshApiDate: Long, PATIENT_API_REFRESH_OFFSET: Int): Boolean {
        return (System.currentTimeMillis()-patientRefreshApiDate>PATIENT_API_REFRESH_OFFSET)
    }

    fun getLocalCurrency(): String = Currency.getInstance(Locale.getDefault()).currencyCode

    fun Context.showLongToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    fun getScale(number: BigDecimal): Int {
        return when {
            number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0 -> 2
            number.compareTo(Double.MAX_VALUE.toBigDecimal())==-1 -> log10(1 / (number.toDouble() % 1)).toInt()+2
            else -> 8
        }
    }


    fun BigDecimal.getNumberInText():String{
        this.precision()
        val scale = getScale(this)
      return  this.stripTrailingZeros().setScale(scale, RoundingMode.HALF_EVEN).toPlainString()
    }

}