package com.srb9181.currencyconverter.data.remote

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiEndPoints {

    @GET("/live")
    suspend fun getLiveCurrenciesConversions(@Query("access_key")key:String):LiveCurrencyModel

}