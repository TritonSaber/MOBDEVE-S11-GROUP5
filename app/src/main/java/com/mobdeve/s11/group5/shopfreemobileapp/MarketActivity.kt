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
import com.google.firebase.storage.storage
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
    private val storage = Firebase.storage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.marketlist = ArrayList<Market>()
        this.marketsBinding = MarketsBinding.inflate(layoutInflater)
        setContentView(marketsBinding.root)

        this.recyclerView = marketsBinding.mList
        this.recyclerView.layoutManager = LinearLayoutManager(this@MarketActivity)

        //upload files

        val storageRef = storage.reference

        //var bm : Bitmap = BitmapFactory.decodeResource(this@MarketActivity.resources, R.drawable.sanmiglight);

        //successful upload

        //var fileUri: Uri = Uri.parse("android.resource://"+ PACKAGE_NAME + "/"+R.drawable.sanmiglight)
        //var fileref = storageRef.child("images/"+fileUri.lastPathSegment)

        //var uploadTask = fileref.putFile(fileUri)

        /*uploadTask.addOnFailureListener { task ->
                Log.d("[TEST]", "Upload Failed: ${task.stackTrace} ")
            }.addOnSuccessListener {
                Log.d("[TEST]", "Successful Upload.")
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.d("[TEST]", "This is the download Uri: $downloadUri")
                }
            }*/


        executorService.execute {
            dbRef = Firebase.firestore

            dbRef.collection(MyFirestoreReferences.MARKET_COLLECTION).get().addOnSuccessListener { documentSnapshots ->
                Log.d("[MARKET]", "Market collection is present")
                collectionexist = true

                Log.d("[MARKET]", documentSnapshots.documents.toString())
                for (document in documentSnapshots) {
                    Log.d("[MARKET]", "${document.id} => ${document.data["mname"]}")

                    var imagefile = storageRef.child(document.data["mdownloadurl"].toString())

                    imagefile.downloadUrl.addOnSuccessListener { image ->
                        var downloadUri = image
                        this.marketlist.add(Market (
                            document.data["mname"].toString(),
                            document.data["mloc"].toString(),
                            document.data["mdesc"].toString(),
                            downloadUri,
                            document.data["mdownloadurl"].toString()
                        ))
                    }

                    runOnUiThread {
                        Log.d("[MARKET]", "UI updated.")
                        this.marketAdapter = MarketAdapter(marketlist)
                        this.recyclerView.adapter = marketAdapter
                    }
                }
            }.addOnFailureListener{exception ->
                Log.d("[MARKET]", "Market Error: $exception")
                Log.d("[MARKET]", "Inserting new data")

                this.marketlist = DataHelper.initializeMarketData()

                for (market in marketlist) {
                    //upload imagefile

                    var path = "images/${market.mImageURI?.lastPathSegment}"

                    var fileref = storageRef.child(path)

                    market.mImageURI?.let {
                        fileref.putFile(it).addOnSuccessListener{
                            Log.d("[MARKET]", "File Uploaded: ${market.mImageURI}")
                            market.mStorageURL = path
                        }
                    }
                    dbRef.collection(MyFirestoreReferences.MARKET_COLLECTION).document(market.mName).set(market).addOnFailureListener{ task ->
                        Log.d("[MARKET]", "Data upload failed: ${task.stackTrace}")
                    }

                    dbRef.collection(MyFirestoreReferences.MARKET_COLLECTION).get().addOnSuccessListener { documentSnapshots ->
                        Log.d("[MARKET]", "Market collection is present")
                        collectionexist = true

                        Log.d("[MARKET]", documentSnapshots.documents.toString())
                        for (document in documentSnapshots) {
                            Log.d("[MARKET]", "${document.id} => ${document.data["mname"]}")

                            var imagefile =
                                storageRef.child(document.data["mdownloadurl"].toString())

                            imagefile.downloadUrl.addOnSuccessListener { image ->
                                var downloadUri = image
                                this.marketlist.add(
                                    Market(
                                        document.data["mname"].toString(),
                                        document.data["mloc"].toString(),
                                        document.data["mdesc"].toString(),
                                        downloadUri,
                                        document.data["mdownloadurl"].toString()
                                    )
                                )
                            }

                            runOnUiThread {
                                Log.d("[MARKET]", "UI updated.")
                                this.marketAdapter = MarketAdapter(marketlist)
                                this.recyclerView.adapter = marketAdapter
                            }
                        }
                    }
                }
            }
        }

        Log.d("[MARKET]", "$marketlist")
    }

    override fun onStart() {
        super.onStart()
        Log.d("[MARKET]", "Market onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("[MARKET]", "Market onResume")
    }

}