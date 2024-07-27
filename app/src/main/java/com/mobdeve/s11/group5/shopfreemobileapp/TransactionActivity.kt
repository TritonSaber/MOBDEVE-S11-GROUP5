package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TransactionHistoryBinding

class TransactionActivity : ComponentActivity () {
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : TransactionHistoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionlist: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = TransactionHistoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //this activity doesn't really mess with any form of data

        this.recyclerView = viewBinding.thList
        this.recyclerView.adapter = TransactionAdapter(transactionlist)
    }
}