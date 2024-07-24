package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProductItemBinding

class ProductAdapter (productlist: ArrayList<Product>, nextActivityResultLauncher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<ProductViewHolder>() {
    private val productlist: ArrayList<Product> = productlist

    private val nextActivityResultLauncher: ActivityResultLauncher<Intent> = nextActivityResultLauncher

    override fun getItemCount(): Int {
        return this.productlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemBinding =
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val productViewHolder = ProductViewHolder(itemBinding)


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