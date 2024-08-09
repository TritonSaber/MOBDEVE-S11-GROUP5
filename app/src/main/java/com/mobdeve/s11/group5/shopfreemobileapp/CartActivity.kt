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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CartBinding
import java.util.Calendar
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartActivity() : ComponentActivity() {
    //this is where the cart.xml will be used
    private lateinit var cartBinding: CartBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var collectionexist: Boolean? = null
    private val storage = Firebase.storage
    private lateinit var cart: ArrayList<CartItem>
    private lateinit var productlist: ArrayList<Product>

    //for the payment activity
    private val myActivityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
        ActivityResultContracts.StartActivityForResult(),
        object : ActivityResultCallback<ActivityResult> {
            override fun onActivityResult(result: ActivityResult) {
                val data = result?.data

                //the payment activity failed
                if (data != null) {
                    if (result!!.resultCode == ResultCodes.FAILED_PAYMENT_RESULT.ordinal) {
                        //don't clear the cart???
                        //make a dialog box appear that says that the payment failed, message in Intent
                    }

                    if (result!!.resultCode == ResultCodes.SUCCESS_PAYMENT_RESULT.ordinal) {
                        cart.clear()
                        cartAdapter.notifyItemRangeRemoved(0, cartAdapter.itemCount)
                    }
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //sample data
        cart = ArrayList<CartItem>()
        this.cartBinding = CartBinding.inflate(layoutInflater)
        setContentView(cartBinding.root)

        var storageRef = storage.reference


        this.recyclerView = cartBinding.cRecycler
        this.recyclerView.setLayoutManager(LinearLayoutManager(this@CartActivity))

        executorService.execute {
            //get the cart from firestore or firebase cache
            //val sample = Product()
            //cart.add(sample)
            //Log.d("[CART]", "${cart}")

            cart = ArrayList<CartItem>()
            productlist = ArrayList()

            //get the cart from the db
            dbRef = Firebase.firestore

            executorService.execute {
                auth = Firebase.auth
                var userrn = auth.currentUser?.uid
                dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION).whereEqualTo("tuserid", userrn).whereEqualTo("tcompleted", false).get().addOnSuccessListener { document ->
                    Log.d("[TRANSACTION]", "Success: ${document.documents.first().id} => ${document.documents.first().data}")

                    var transaction = document.documents.first().id
                    var breakdownlist = document.documents.first().data!!["cart"]

                    Log.d("[TRANSACTION]", "Cart Internals: $breakdownlist")

                    for (item in breakdownlist as ArrayList<*>) {
                        var convitem = item as Map<*, *>
                        Log.d("[TRANSACTION]", "value pairs: ${convitem}")

                        dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION).document(convitem["productUID"].toString()).get().addOnSuccessListener { document ->
                            //get the image
                            Log.d("[TRANSACTION]", "Document: ${document.data!!["pname"]}")
                            var imageref = storageRef.child(document.data!!["pstorageURL"].toString())

                            imageref.downloadUrl.addOnSuccessListener { image ->
                                productlist.add(
                                    Product(
                                        document.data!!["pname"].toString(),
                                        document.data!!["plocId"].toString(),
                                        document.data!!["pprice"].toString().toDouble(),
                                        document.data!!["pstorageURL"].toString(),
                                        image,
                                        convitem["quantity"].toString().toInt(),
                                        document.data!!["pdesc"].toString(),
                                        document.data!!["pcategory"].toString(),
                                        document.data!!["pperWeight"].toString()
                                    )
                                )
                            }.addOnCompleteListener {
                                runOnUiThread {
                                    Log.d("[TRANSACTION]", "Productlist before adapter: $productlist")
                                    //cartAdapter code + myActivityResultLauncher
                                    this.cartAdapter = CartAdapter(productlist, myActivityResultLauncher, this@CartActivity)
                                    this.recyclerView.setAdapter(cartAdapter)
                                }
                            }
                        }.addOnFailureListener { task ->
                            Log.d("[CART-Adapter]", "Failed: ${task.stackTrace}")
                        }
                    }
                    /*dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                        .document(transaction)
                        .collection("cart")
                        .get().addOnSuccessListener { documents ->
                        for (doc in documents) {
                            Log.d("[TRANSACTION]", "Cart contents: ${doc.id} => ${doc.data}")
                        }
                    }.addOnFailureListener { task ->
                        Log.d("[TRANSACTION]", "Failed Cart get: ${task.stackTrace}")
                    }.addOnCompleteListener {
                        runOnUiThread {
                            //cartAdapter code + myActivityResultLauncher
                            this.cartAdapter = CartAdapter(cart, myActivityResultLauncher)
                            this.recyclerView.setAdapter(cartAdapter)
                        }
                    }*/
                }.addOnFailureListener {
                    //make the user transaction
                    var usercart = Transaction(
                        userrn,
                        Calendar.getInstance().time.toString(),
                        0.00,
                        cart,
                        false
                    )
                    dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION).document().set(usercart).addOnSuccessListener {
                        Log.d("[TRANSACTION]", "User cart generated")
                    }.addOnFailureListener { task ->
                        Log.d("[TRANSACTION]", "Failed to create: ${task.stackTrace} ")
                    }
                }
            }


        }


    }
}

