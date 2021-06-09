package com.srb9181.currencyconverter.data.remote


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LiveCurrencyModel(
    @SerializedName("privacy")
    var privacy: String?,
    @SerializedName("quotes")
    var quotes: Map<String,Double>,
    @SerializedName("source")
    var source: String,
    @SerializedName("success")
    var success: Boolean,
    @SerializedName("terms")
    var terms: String?,
    @SerializedName("timestamp")
    var timestamp: Long,
    @SerializedName("error")
    var error: Error?,
){
    @Keep
    data class Error(
        @SerializedName("code")
        var code: Int?,
        @SerializedName("info")
        var info: String?,
        @SerializedName("type")
        var type: String?
    )

}
