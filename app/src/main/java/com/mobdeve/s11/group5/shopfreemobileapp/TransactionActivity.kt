package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TransactionHistoryBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TransactionActivity : ComponentActivity () {
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : TransactionHistoryBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionlist: ArrayList<Transaction>
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = TransactionHistoryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        dbRef = Firebase.firestore
        auth = Firebase.auth

        //this activity doesn't really mess with any form of data
        executorService.execute {
            //get the user's completed transactions
            dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                .whereEqualTo("tuserid", auth.currentUser?.uid)
                .whereEqualTo("tcompleted", true)
                .get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        transactionlist.add(
                            Transaction (
                                document.data["tuserid"].toString(),
                                document.data["tdate"].toString(),
                                document.data["ttotal"].toString().toDouble(),
                                null,
                                document.data["tcompleted"].toString().toBoolean()
                            )
                        )
                        runOnUiThread {
                            this.recyclerView = viewBinding.thList
                            this.recyclerView.adapter = TransactionAdapter(transactionlist)
                        }
                    }
                }
        }


    }
}