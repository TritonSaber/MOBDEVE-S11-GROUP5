package com.mobdeve.s11.group5.shopfreemobileapp

import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TransactionHistoryItemBinding

class TransactionViewHolder (private val itemBinding: TransactionHistoryItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
    private var thiNum: Button = itemBinding.thiNum
    private var thiDateAndTime: TextView = itemBinding.thiDateAndTime
    private var thiLocation: TextView = itemBinding.thiLocation
    private var thiName: TextView = itemBinding.thiName
    private var thiTotalItems: TextView = itemBinding.thiTotalItems
    private var thiTotal: TextView = itWemBinding.thiTotal

    fun bindData (t: Transaction) {
        thiDateAndTime.text = t.tDate.toString()
        thiLocation.text = t.tLocName
        thiTotalItems.text = t.tCart.size.toString()
        thiTotal.text = t.tTotal.toString()
    }

}