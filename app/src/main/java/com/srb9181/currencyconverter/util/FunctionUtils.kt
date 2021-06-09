package com.srb9181.currencyconverter.util

import android.content.Context
import android.widget.Toast
import java.util.*

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

}