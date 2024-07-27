package com.mobdeve.s11.group5.shopfreemobileapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.MarketsBinding
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CategoryActivity: ComponentActivity() {
    private lateinit var marketBinding: MarketsBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var dbRef: FirebaseFirestore
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var collectionexist: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.categoryList = ArrayList<Category>()
        this.marketBinding = MarketsBinding.inflate(layoutInflater)
        setContentView(marketBinding.root)

        this.recyclerView = marketBinding.mList
        this.recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)

//        executorService.execute {
//            dbRef = Firebase.firestore
//            dbRef.collection(MyFirestoreReferences.MARKET_COLLECTION).get()
//                .addOnSuccessListener { documentSnapshots ->
//                    Log.d("[CATEGORY]", "CATEGORY collection is present")
//                    collectionexist = true
//
//                    Log.d("[CATEGORY]", documentSnapshots.documents.toString())
//                    for (document in documentSnapshots) {
//                        Log.d("[CATEGORY]", "${document.id} => ${document.data["cName"]}")
//                        this.categoryList.add(
//                            Category(
//                                document.data["cName"].toString(),
//                                document.data["cImage"].toString().toInt()
//                            )
//                        )
//
//                        runOnUiThread {
//                            Log.d("[CATEGORY]", "UI updated.")
//                            this.categoryAdapter = CategoryAdapter(categoryList)
//                            this.recyclerView.adapter = categoryAdapter
//                        }
//                    }
//                }.addOnFailureListener { exception ->
//                Log.d("[CATEGORY]", "CATEGORY Error: $exception")
//                Log.d("[CATEGORY]", "Inserting new data")
//            }
//        }
//
//        Log.d("[CATEGORY]", "$categoryList")


    }
}