package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartActivity() : ComponentActivity() {
    //this is where the cart.xml will be used
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var collectionexist: Boolean? = null
    private val storage = Firebase.storage
    private lateinit var cart: ArrayList<Product>

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
        setContentView(R.layout.cart)

        //sample data
        cart = ArrayList<Product>()


        this.recyclerView = findViewById(R.id.cRecycler)
        this.recyclerView.setLayoutManager(LinearLayoutManager(this@CartActivity))

        executorService.execute {
            //get the cart from firestore or firebase cache
            //val sample = Product()
            //cart.add(sample)
            //Log.d("[CART]", "${cart}")

            //get the cart from the db
            dbRef = Firebase.firestore

            executorService.execute {
                auth = Firebase.auth
                var userrn = auth.currentUser?.uid
                dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION).whereEqualTo("userid", userrn).get().addOnSuccessListener {

                }.addOnFailureListener {
                    //make the user transaction

                }
            }

            runOnUiThread {
                //cartAdapter code + myActivityResultLauncher
                this.cartAdapter = CartAdapter(cart, myActivityResultLauncher)
                this.recyclerView.setAdapter(cartAdapter)
            }
        }


    }
}