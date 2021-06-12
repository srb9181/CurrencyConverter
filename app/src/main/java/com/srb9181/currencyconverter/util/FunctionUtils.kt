package com.srb9181.currencyconverter.util

import android.content.Context
import android.widget.Toast
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.math.log10

object FunctionUtils {

    fun isTimeToRefresh(patientRefreshApiDate: Long, PATIENT_API_REFRESH_OFFSET: Int): Boolean {
        val cal= Calendar.getInstance()
        cal.timeInMillis = patientRefreshApiDate
        cal.add(Calendar.HOUR_OF_DAY,PATIENT_API_REFRESH_OFFSET )
        return (System.currentTimeMillis()>cal.timeInMillis).also { cal.clear() }
    }

    fun getLocalCurrency(): String = Currency.getInstance(Locale.getDefault()).currencyCode

    fun Context.showLongToast(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
    }

    fun getScale(number: BigDecimal): Int {
        return if (number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0)
            0
        else log10(1 / (number.toDouble() / 1)).toInt() + 2
    }

    fun BigDecimal.getNumberInText():String{
        val scale =getScale(this)
      return  this.stripTrailingZeros().setScale(scale, RoundingMode.HALF_EVEN).toPlainString()
    }

}