package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TransactionHistoryItemBinding

class TransactionAdapter (transactionlist: ArrayList<Transaction>, nextActivityResultLauncher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<TransactionViewHolder>()  {
    private var transactionlist: ArrayList<Transaction> = transactionlist

    private val nextActivityResultLauncher: ActivityResultLauncher<Intent> = nextActivityResultLauncher

    /*
        Things that we need this to do:
            - on Click of X we remove te item from cart
            - clicking confirm payment goes to the Payment activity
     */

    override fun getItemCount(): Int {
        return this.transactionlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemBinding = TransactionHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val transactionViewHolder = TransactionViewHolder(itemBinding)

        //delete logic
        //val db = Firebase.firestore

        transactionViewHolder.itemView.setOnClickListener {
            //go to individual page
        }

        return transactionViewHolder
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.bindData(this.transactionlist[position])
    }

}