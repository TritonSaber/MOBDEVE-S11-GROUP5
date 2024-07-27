package com.mobdeve.s11.group5.shopfreemobileapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TransactionItemBinding

class TransactionHistoryAdapter (productlist: ArrayList<Product>): RecyclerView.Adapter<TransactionHistoryViewHolder>() {
    private var productlist: ArrayList<Product> = productlist

    override fun getItemCount(): Int {
        return this.productlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionHistoryViewHolder {
        val itemBinding = TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val transactionHistoryViewHolder = TransactionHistoryViewHolder(itemBinding)

        //delete logic
        //val db = Firebase.firestore

        transactionHistoryViewHolder.itemView.setOnClickListener {
            //go to individual page
        }

        return transactionHistoryViewHolder
    }

    override fun onBindViewHolder(holder: TransactionHistoryViewHolder, position: Int) {
        holder.bindData(this.productlist[position])
    }

}