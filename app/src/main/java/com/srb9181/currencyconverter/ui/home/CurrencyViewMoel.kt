package com.srb9181.currencyconverter.ui.home

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.*
import com.srb9181.currencyconverter.R
import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import com.srb9181.currencyconverter.repository.CurrencyConverterRepository
import com.srb9181.currencyconverter.util.Constants
import com.srb9181.currencyconverter.util.FunctionUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val currencyConverterRepository: CurrencyConverterRepository,
    private val preferences: SharedPreferences,private val application: Application): ViewModel() {
    private val _msgLivedata = MutableLiveData<String>()
    val msgLiveData:LiveData<String> get() =_msgLivedata
    private val _loadingLivedata = MutableLiveData<Boolean>(false)
    val loadingLiveData:LiveData<Boolean>
    get() = _loadingLivedata

    fun getCurrencies() {
        viewModelScope.launch {
             val time =preferences.getLong(Constants.LAST_API_TIME,0)
            if(FunctionUtils.isTimeToRefresh(time,Constants.API_CALL_OFFSET) || time==0L)
            currencyConverterRepository.getAllCurrenciesFromApi(application.getString(R.string.api_key))
                .onStart { _loadingLivedata.postValue(true) }
                .catch { error ->
                    _msgLivedata.postValue(error.localizedMessage)
                    _loadingLivedata.postValue(false)
                }
                .onCompletion {
                    _loadingLivedata.postValue(false)
                    _msgLivedata.postValue(application.getString(R.string.success_msg))
                }
                .collect {
                    preferences.edit {
                        putLong(Constants.LAST_API_TIME,it*1000)
                    }
                }


        }
    }


    val currencyLiveData:LiveData<List<CurrencyConversionRates>> = currencyConverterRepository
        .getAllCurrencies().onStart {  }
        .catch {  }
        .asLiveData()

}