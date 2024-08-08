package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Context
import android.content.Intent
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
        val itemBinding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val productViewHolder = ProductViewHolder(itemBinding)

        //open up a new activity
        var intent = Intent(activitycontext, ProductBuyActivity::class.java)

        intent.putExtra(IntentKey.PRODUCT_KEY, productlist[productViewHolder.bindingAdapterPosition].pName)

        //launch the new activity


        return productViewHolder
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bindData(this.productlist[position])

        holder.itemView.setOnClickListener {
            //upon clicking goes to the choosing locations page thingy
            var intent = Intent()

            //launch activity


        }
    }


}