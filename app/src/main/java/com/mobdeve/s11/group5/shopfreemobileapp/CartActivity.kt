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
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartActivity() : ComponentActivity() {
    //this is where the cart.xml will be used
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var cart: ArrayList<Product> = arrayListOf<Product>()

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


        this.recyclerView = findViewById(R.id.cRecycler)
        this.recyclerView.setLayoutManager(LinearLayoutManager(this@CartActivity))

        executorService.execute {
            //get the cart from firestore or firebase cache
            val sample = Product(1, "San Miguel Light", 1, 50.00, R.drawable.sanmiglight, 0, "250 ml")
            cart.add(sample)
            Log.d("[CART]", "${cart}")

            runOnUiThread {
                //cartAdapter code + myActivityResultLauncher
                this.cartAdapter = CartAdapter(cart, myActivityResultLauncher)
                this.recyclerView.setAdapter(cartAdapter)
            }
        }


    }
}