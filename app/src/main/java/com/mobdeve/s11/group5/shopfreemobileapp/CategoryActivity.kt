package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.MarketsBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CategoryActivity: ComponentActivity() {
    private lateinit var marketBinding: MarketsBinding
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var dbRef: FirebaseFirestore
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val storage = Firebase.storage
    private var collectionexist: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("[CATEGORY]", "Category put into view.")

        this.categoryList = ArrayList<Category>()
        this.marketBinding = MarketsBinding.inflate(layoutInflater)
        setContentView(marketBinding.root)

        this.marketBinding.pCategory.text = "Categories"

        this.recyclerView = marketBinding.mList
        this.recyclerView.layoutManager = LinearLayoutManager(this@CategoryActivity)

        val storageRef = storage.reference

        executorService.execute {
            dbRef = Firebase.firestore
            //get the data needed
            dbRef.collection(MyFirestoreReferences.CATEGORY_COLLECTION).get()
                .addOnSuccessListener { documents ->
                    var counter = 0

                    for (document in documents) {
                        //cull it down a little bit
                        Log.d("[CATEGORY]", "${document.id} => ${document.data}")
                        var imagefile = storageRef.child(document.data["cstorageURL"].toString())
                        imagefile.downloadUrl.addOnSuccessListener { image ->
                            var downloaduri = image

                            this.categoryList.add(
                                Category(
                                    document.data["ccategoryName"].toString(),
                                    downloaduri,
                                    document.data["cstorageURL"].toString()
                                )
                            )
                        }.addOnFailureListener { task ->
                            Log.d("[CATEGORY]", "${task.stackTrace}")
                        }.addOnCompleteListener {
                            Log.d("[CATEGORY]", "Categorylist: $categoryList")
                            runOnUiThread {
                                Log.d("[Category]", "UI updated.")
                                this.categoryAdapter = CategoryAdapter(categoryList, this@CategoryActivity)
                                this.recyclerView.adapter = categoryAdapter
                            }
                        }
                    }
                }.addOnFailureListener {
                Log.d("[CATEGORY]", "Failure: Initializing Categories")
                this.categoryList = DataHelper.initializeCategoryData()

                for (category in categoryList) {
                    var path = "images/${category.cImageURI?.lastPathSegment}"

                    var fileref = storageRef.child(path)

                    category.cImageURI?.let {
                        fileref.putFile(it).addOnSuccessListener {
                            Log.d("[CATEGORY]", "Category List has been created")
                            category.cStorageURL = path
                        }
                    }
                    dbRef.collection(MyFirestoreReferences.CATEGORY_COLLECTION)
                        .document(category.cCategoryName).set(category)
                        .addOnFailureListener { task ->
                            Log.d("[CATEGORY]", "Data upload failed: ${task.stackTrace}")
                        }
                }
            }
        }
    }
}