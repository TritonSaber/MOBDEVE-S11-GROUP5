package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProfilePageBinding

class ProfileActivity : ComponentActivity () {
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : ProfilePageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionlist: ArrayList<Transaction>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = ProfilePageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //empty for testing
        transactionlist = ArrayList<Transaction>()

        this.recyclerView = viewBinding.ppTransactionHistory
        this.recyclerView.adapter = TransactionAdapter(transactionlist)

        viewBinding.ppEdit.setOnClickListener {
            //not expecting any callbacks
            val intent = Intent(this@ProfileActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }

}