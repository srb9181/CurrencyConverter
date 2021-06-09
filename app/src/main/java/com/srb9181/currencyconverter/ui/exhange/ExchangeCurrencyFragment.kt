package com.srb9181.currencyconverter.ui.exhange

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.srb9181.currencyconverter.R
import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import com.srb9181.currencyconverter.databinding.FragmentExchangeBinding
import com.srb9181.currencyconverter.ui.home.CurrencyViewModel
import com.srb9181.currencyconverter.util.Constants
import com.srb9181.currencyconverter.util.FunctionUtils
import java.math.MathContext
import java.math.RoundingMode

class ExchangeCurrencyFragment:Fragment(R.layout.fragment_exchange) {

    private var binding: FragmentExchangeBinding? = null
    private val currencyViewModel by activityViewModels<CurrencyViewModel>()
    private lateinit var currenciesList:MutableList<CurrencyConversionRates>
    private lateinit var currenciesCodeList:MutableList<String>
    var sourceCurrencyCode = "USD"
    var sourceCurrencyRate = 1.0
    var resultCurrencyRate = 0.0
    var resultCurrencyCode = ""



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentExchangeBinding.bind(view)
        binding =_binding

        binding!!.etSourceCurrency.setSelection(0)
        binding!!.etSourceCurrency.requestFocus()
        observeViewModels()

       binding?.fabInterchange?.setOnClickListener {
           sourceCurrencyRate =resultCurrencyRate.also { resultCurrencyRate=sourceCurrencyRate }
           sourceCurrencyCode =resultCurrencyCode.also { resultCurrencyCode=sourceCurrencyCode }
          setAdapterData()
       }

        binding?.buttonConvert?.setOnClickListener {
            setCurrencyInUi()
        }

    }

    private fun observeViewModels() {
        currencyViewModel.currencyLiveData.observe(viewLifecycleOwner){
            if(it.isNotEmpty()){
                sourceCurrencyCode =it.first().currencySource
                sourceCurrencyRate =it.first().currencySourceRate
                resultCurrencyCode =FunctionUtils.getLocalCurrency()
                resultCurrencyRate = it.filter { it.currencyCode==resultCurrencyCode }.first().currencyRate
                setDataInUi(it)
            }

        }
    }

    private fun setDataInUi(currencyConversionRatesList: List<CurrencyConversionRates>) {
        currenciesList= currencyConversionRatesList.toMutableList()
        currenciesCodeList =currenciesList.map { it.currencyCode }.toMutableList()
        val sourceCurrArrayAdapter = ArrayAdapter<String>(requireContext(),R.layout.spinner_item_currency,currenciesList.map { it.currencyCode })
        sourceCurrArrayAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown)
        val resultCurrArrayAdapter = ArrayAdapter<String>(requireContext(),R.layout.spinner_item_currency,currenciesList.map { it.currencyCode })
        resultCurrArrayAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown)

        binding!!.sourceCurrencySpinner.adapter = sourceCurrArrayAdapter
        binding!!.resultCurrencySpinner.adapter = resultCurrArrayAdapter

        binding!!.sourceCurrencySpinner.setSelection(currenciesCodeList.indexOf(sourceCurrencyCode))
        binding!!.resultCurrencySpinner.setSelection(currenciesCodeList.indexOf(resultCurrencyCode))

        binding?.etSourceCurrency?.setText(sourceCurrencyRate.toString())
        binding?.etResultCurrency?.setText(resultCurrencyRate.toString())

        binding!!.sourceCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                val selectedCurrency = currencyConversionRatesList[position]
                sourceCurrencyRate = selectedCurrency.currencyRate
                sourceCurrencyCode = selectedCurrency.currencyCode
                setCurrencyInUi()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding!!.resultCurrencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?,
                position: Int, id: Long
            ) {
                val selectedCurrency = currencyConversionRatesList[position]
                resultCurrencyRate = selectedCurrency.currencyRate
                resultCurrencyCode = selectedCurrency.currencyCode
                setCurrencyInUi()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    private fun setCurrencyInUi() {
        binding?.etSourceCurrency?.let {
            it.setText((1.toBigDecimal().multiply(it.text.toString().toBigDecimal()).setScale(2,RoundingMode.HALF_EVEN).toPlainString()))
        }


        binding?.etResultCurrency?.let {
            if(sourceCurrencyCode==resultCurrencyCode){
                it.setText(binding!!.etSourceCurrency.text.toString())
            }
            else if(resultCurrencyCode==Constants.BTC){
                it.setText((1.0.toBigDecimal().divide(sourceCurrencyRate.toBigDecimal(),2,RoundingMode.HALF_DOWN)).multiply(resultCurrencyRate.toBigDecimal()).multiply(
                    binding!!.etSourceCurrency.text.toString().toBigDecimal()).setScale(12  ,RoundingMode.HALF_EVEN).toPlainString())
            }
            else
                it.setText((1.0.toBigDecimal().divide(sourceCurrencyRate.toBigDecimal(),2,RoundingMode.HALF_DOWN)).multiply(resultCurrencyRate.toBigDecimal()).multiply(
                        binding!!.etSourceCurrency.text.toString().toBigDecimal()).setScale(2,RoundingMode.HALF_EVEN).toPlainString())
        }
    }

    fun setCurrencyData(currencyConversionRates: CurrencyConversionRates){
        resultCurrencyRate = currencyConversionRates.currencyRate
        resultCurrencyCode = currencyConversionRates.currencyCode
        setAdapterData()
    }


    private fun setAdapterData() {
        binding?.sourceCurrencySpinner?.setSelection(currenciesCodeList.indexOf(sourceCurrencyCode))
        binding?.resultCurrencySpinner?.setSelection(currenciesCodeList.indexOf(resultCurrencyCode))
        setCurrencyInUi()
    }



    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        binding = null
        super.onDestroyView()
    }


}