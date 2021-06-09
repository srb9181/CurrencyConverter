package com.srb9181.currencyconverter.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.srb9181.currencyconverter.R
import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import com.srb9181.currencyconverter.databinding.ActivityMainBinding
import com.srb9181.currencyconverter.ui.adapters.CurrencyPagerAdapter
import com.srb9181.currencyconverter.ui.exhange.ExchangeCurrencyFragment
import com.srb9181.currencyconverter.util.FunctionUtils.showLongToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnCurrencyClicked {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private val currencyPageAdapter by lazy { CurrencyPagerAdapter(this) }
    var doubleBackToExitPressedOnce = false
    private val currencyViewModel by viewModels<CurrencyViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.contentLayout.viewPager.adapter =currencyPageAdapter

        currencyViewModel.getCurrencies()
        TabLayoutMediator( binding.contentLayout.tabLayout, binding.contentLayout.viewPager) { tab, position ->

            if(position==0) {
                tab.text = "Exchange Currency"
            }
            else {
                tab.text = "Currency Rates"
            }
        }.attach()
        binding.contentLayout.viewPager.offscreenPageLimit=2

        binding.contentLayout.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
            }

            override fun onTabReselected(tab: TabLayout.Tab) {
            }

        })

        observeViewModels()

    }

    private fun observeViewModels() {
        currencyViewModel.loadingLiveData.observe(this){
            if(it)
                binding.contentLayout.progressBar.visibility =View.VISIBLE
            else
                binding.contentLayout.progressBar.visibility =View.GONE
        }

        currencyViewModel.msgLiveData.observe(this){
            this@MainActivity.showLongToast(it)
        }
    }

    override fun onCurrencyConversionClicked(currency: CurrencyConversionRates) {
        for (frag in supportFragmentManager.fragments){
            if(frag is ExchangeCurrencyFragment && frag.isAdded){
                frag.setCurrencyData(currency)
                binding.contentLayout.viewPager.setCurrentItem(1,true)

            }
        }

    }


    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }

        doubleBackToExitPressedOnce = true
        Toast.makeText(this, getString(R.string.exit_msg_text), Toast.LENGTH_SHORT).show()

        Handler(mainLooper).postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)

    }

}

interface  OnCurrencyClicked{
    fun onCurrencyConversionClicked(currency : CurrencyConversionRates)
}