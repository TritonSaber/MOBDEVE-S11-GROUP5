package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProductItemBinding

class ProductAdapter (productlist: ArrayList<Product>, nextActivityResultLauncher: ActivityResultLauncher<Intent>, activityContext: Context): RecyclerView.Adapter<ProductViewHolder>() {
    private val productlist: ArrayList<Product> = productlist
    private val nextActivityResultLauncher: ActivityResultLauncher<Intent> = nextActivityResultLauncher
    private val activitycontext: Context = activityContext


    override fun getItemCount(): Int {
        return this.productlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val productViewHolder = ProductViewHolder(itemBinding)
        //open up a new activity
        val intent = Intent(activitycontext, ProductBuyActivity::class.java)
        itemBinding.piImage.setOnClickListener {
            Log.d("[PRODUCT]", "Index: ${productViewHolder.bindingAdapterPosition}")

            intent.putExtra(IntentKey.PRODUCT_KEY, productlist[productViewHolder.bindingAdapterPosition].pName)

            activitycontext.startActivity(intent)
        }

        itemBinding.piLayout.setOnClickListener {
            Log.d("[PRODUCT]", "Index: ${productViewHolder.bindingAdapterPosition}")

            intent.putExtra(IntentKey.PRODUCT_KEY, productlist[productViewHolder.bindingAdapterPosition].pName)

            activitycontext.startActivity(intent)
        }



        //launch the new activity
        return productViewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindData(this.productlist[position])
    }


}