package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProductsBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProductActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var dbRef: FirebaseFirestore
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var productList: ArrayList<Product> = ArrayList<Product>()
    private val storage = Firebase.storage

    private val myActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult> {
            override fun onActivityResult(result: ActivityResult) {
                val data = result?.data

                //the payment activity failed
                if (data != null) {
                    if (result!!.resultCode == ResultCodes.FAILED_ADD_CART_RESULT.ordinal) {
                        //return to this page with the

                    }

                    if (result!!.resultCode == ResultCodes.SUCCESS_ADD_CART_RESULT.ordinal) {
                        //add it to the online version of the cart (firebase)
                    }
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var viewBinding = ProductsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        this.recyclerView = viewBinding.pRecycler
        this.recyclerView.setLayoutManager(LinearLayoutManager(this@ProductActivity))

        viewBinding.cpassBack.setOnClickListener {
            finish()
        }

        executorService.execute {
            var intent = intent
            var category: String? = intent.getStringExtra(IntentKey.CATEGORY_KEY)
            viewBinding.pCategory.text = category

            //based on category get the stuff

            executorService.execute {
                dbRef = Firebase.firestore

                var storageRef = storage.reference

                dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION).whereEqualTo("pcategory", category).get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        Log.d("[PRODUCT]", "${document.id} => ${document.data}")
                        var imagefile = storageRef.child(document.data["pstorageURL"].toString())
                        imagefile.downloadUrl.addOnSuccessListener { image ->
                            var downloaduri = image

                            this.productList.add(
                                Product(
                                    document.data["pname"].toString(),
                                    document.data["plocId"].toString(),
                                    document.data["pprice"].toString().toDouble(),
                                    document.data["pstorageURL"].toString(),
                                    downloaduri,
                                    null, //no need for this yet
                                    document.data["pdesc"].toString(),
                                    document.data["pcategory"].toString(),
                                    document.data["pperWeight"].toString()
                                )
                            )
                        }.addOnFailureListener { task ->
                            Log.d("[PRODUCT]", "${task.stackTrace}")
                        }.addOnCompleteListener {
                            runOnUiThread {
                                Log.d("[PRODUCT]", "UI updated, Productlist => ${productList}")
                                this.productAdapter = ProductAdapter(productList, myActivityResultLauncher, this@ProductActivity)
                                this.recyclerView.adapter = productAdapter
                            }
                        }
                    }
                }.addOnFailureListener {
                    productList = DataHelper.inititalizeProductData()
                    for (product in productList) {

                        var fileref = storageRef.child(product.pStorageURL)

                        product.pImageUri?.let {
                            fileref.putFile(it).addOnSuccessListener {
                                Log.d("[PRODUCT]", "Product List has been created")
                            }
                        }
                        dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION).document().set(product).addOnFailureListener {task ->
                            Log.d("[PRODUCT]", "Data upload failed: ${task.stackTrace}")
                        }
                    }

                    dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION).whereEqualTo("pcategory", category).get().addOnSuccessListener { documents ->
                        for (document in documents) {
                            Log.d("[PRODUCT]", "${document.id} => ${document.data}")
                            var imagefile = storageRef.child(document.data["pstorageURL"].toString())
                            imagefile.downloadUrl.addOnSuccessListener { image ->
                                var downloaduri = image

                                this.productList.add(
                                    Product(
                                        document.data["pname"].toString(),
                                        document.data["plocId"].toString(),
                                        document.data["pprice"].toString().toDouble(),
                                        document.data["pstorageURL"].toString(),
                                        downloaduri,
                                        null, //no need for this yet
                                        document.data["pdesc"].toString(),
                                        document.data["pcategory"].toString(),
                                        document.data["pperWeight"].toString()
                                    )
                                )
                            }.addOnFailureListener { task ->
                                Log.d("[PRODUCT]", "${task.stackTrace}")
                            }.addOnCompleteListener {
                                Log.d("[PRODUCT]", "Categorylist: $productList")
                                runOnUiThread {
                                    Log.d("[PRODUCT]", "UI updated.")
                                    this.productAdapter = ProductAdapter(productList, myActivityResultLauncher, this@ProductActivity)
                                    this.recyclerView.adapter = productAdapter
                                }
                            }
                        }
                    }

                }

            }
        }


    }
}