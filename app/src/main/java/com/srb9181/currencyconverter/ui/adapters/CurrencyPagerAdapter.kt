package com.srb9181.currencyconverter.ui.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.srb9181.currencyconverter.ui.currencylist.CurrencyListFragment
import com.srb9181.currencyconverter.ui.exhange.ExchangeCurrencyFragment

class CurrencyPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity){


    override fun getItemCount()=2

    override fun createFragment(position: Int): Fragment {
      /*  val bundle = Bundle()
        bundle.putString(Constants.EXTRA_CHANNEL_SID, channelSid)
        bundle.putString("memberRole", memberRole)*/
        return  if(position==0) CurrencyListFragment()//.apply { arguments =bundle }
        else ExchangeCurrencyFragment()//.apply { arguments=bundle }
    }
}