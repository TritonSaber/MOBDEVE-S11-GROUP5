package com.mobdeve.s11.group5.shopfreemobileapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.MarketItemBinding

class MarketAdapter(marketList: ArrayList<Market>): RecyclerView.Adapter<MarketViewHolder>() {
    private val marketList: ArrayList<Market> = marketList

    /*
        Things that we need this to do:
            - on Click of X we remove te item from cart
            - clicking confirm payment goes to the Payment activity
     */

    override fun getItemCount(): Int {
        return this.marketList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketViewHolder {
        val itemBinding = MarketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val marketViewHolder = MarketViewHolder(itemBinding)

        return marketViewHolder
    }

    override fun onBindViewHolder(holder: MarketViewHolder, position: Int) {
        holder.bindData(this.marketList[position])
    }
}