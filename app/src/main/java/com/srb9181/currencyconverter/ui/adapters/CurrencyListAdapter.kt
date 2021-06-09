package com.srb9181.currencyconverter.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.srb9181.currencyconverter.R
import com.srb9181.currencyconverter.data.local.CurrencyConversionRates
import com.srb9181.currencyconverter.databinding.ItemCurrencyBinding
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

class CurrencyListAdapter(
   private var data: List<CurrencyConversionRates> = ArrayList(),
    val onItemClickListener: OnItemClickListener
    ) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyView {
        return CurrencyView(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_currency, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CurrencyView, position: Int) = holder.bind(data[position],onItemClickListener)

    fun swapData(data: List<CurrencyConversionRates>) {
        this.data = data
        notifyDataSetChanged()
    }

    class CurrencyView(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemCurrencyBinding = ItemCurrencyBinding.bind(itemView)
        fun bind(item: CurrencyConversionRates, onItemClickListener: OnItemClickListener) = with(itemView) {
            itemCurrencyBinding.tvCurrencySource.text = item.currencySource
            itemCurrencyBinding.tvCurrencyResult.text = item.currencyCode
            itemCurrencyBinding.tvSourceRate.text = item.currencySourceRate.toString()
            itemCurrencyBinding.tvRate.text =BigDecimal(item.currencyRate).setScale(5,RoundingMode.HALF_EVEN).toPlainString()// String.format("%.3f",  item.currencyRate)
            setOnClickListener {
                onItemClickListener.onItemClick(item)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(item: CurrencyConversionRates)
    }

}