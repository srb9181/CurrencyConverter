package com.srb9181.currencyconverter.ui.currencylist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.srb9181.currencyconverter.R
import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import com.srb9181.currencyconverter.databinding.FragmentRatesBinding
import com.srb9181.currencyconverter.ui.adapters.CurrencyListAdapter
import com.srb9181.currencyconverter.ui.home.CurrencyViewModel
import com.srb9181.currencyconverter.ui.home.OnCurrencyClicked

class CurrencyListFragment:Fragment(R.layout.fragment_rates),
    CurrencyListAdapter.OnItemClickListener {

    private var binding: FragmentRatesBinding? = null
    private val currencyViewModel by activityViewModels<CurrencyViewModel>()

    private val onCurrencyClicked by lazy { requireActivity() as OnCurrencyClicked  }
    private val currencyListAdapter by lazy { CurrencyListAdapter(mutableListOf(),this) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentRatesBinding.bind(view)
        binding =_binding

        binding!!.ratesRecyclerView.adapter= currencyListAdapter

        observeViewModels()


    }

    private fun observeViewModels() {
        currencyViewModel.currencyLiveData.observe(viewLifecycleOwner){
            Log.d("SRB9181","currencies Size ${it.size}")
            currencyListAdapter.swapData(it)
        }
    }


    override fun onDestroyView() {
        // Consider not storing the binding instance in a field, if not needed.
        binding = null
        super.onDestroyView()
    }

    override fun onItemClick(item: CurrencyConversionRates) {
        onCurrencyClicked.onCurrencyConversionClicked(item)
    }
}