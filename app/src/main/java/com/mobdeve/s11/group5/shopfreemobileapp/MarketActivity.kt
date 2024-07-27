package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.MarketsBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MarketActivity: ComponentActivity() {
    private lateinit var marketsBinding: MarketsBinding
    private lateinit var marketAdapter: MarketAdapter
    private lateinit var recyclerView : RecyclerView
    private lateinit var marketlist: ArrayList<Market>
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var collectionexist: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        this.marketlist = DataHelper.initializeMarketData()
        this.marketsBinding = MarketsBinding.inflate(layoutInflater)
        setContentView(marketsBinding.root)

        this.recyclerView = marketsBinding.mList
        this.recyclerView.layoutManager = LinearLayoutManager(this@MarketActivity)

        executorService.execute {
            dbRef = Firebase.firestore
            dbRef.collection(MyFirestoreReferences.MARKET_COLLECTION).get().addOnSuccessListener {
                Log.d("[MARKET]", "Market collection is present")
                collectionexist = true
            }.addOnFailureListener{exception ->
                Log.d("[MARKET]", "Market Error: $exception")
                Log.d("[MARKET]", "Inserting new data")
                var marketcollection = dbRef.collection(MyFirestoreReferences.MARKET_COLLECTION)
                for (market in marketlist) {
                    marketcollection.document().set(market)
                }
            }

            runOnUiThread {
                this.marketAdapter = MarketAdapter(marketlist)
                this.recyclerView.adapter = marketAdapter
            }
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d("[MARKET]", "onStart called")

    }
}