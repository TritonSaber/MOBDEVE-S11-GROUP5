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
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProductsBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProductActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var productList: ArrayList<Product> = arrayListOf<Product>()

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
            //get the cart from firestore or firebase cache
            val sample = Product(1, "San Miguel Light", 1, 50.00, R.drawable.sanmiglight, 0, "250 ml", "Drinks",null)
            productList.add(sample)
            Log.d("[CART]", "${productList}")

            runOnUiThread {
                //cartAdapter code + myActivityResultLauncher
                this.productAdapter = ProductAdapter(productList, myActivityResultLauncher)
                this.recyclerView.setAdapter(productAdapter)
            }
        }


    }
}