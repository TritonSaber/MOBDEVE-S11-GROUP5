package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CartItemBinding

class CartAdapter (cart: ArrayList<Product>, nextActivityResultLauncher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<CartViewHolder>() {
    private val cart: ArrayList<Product> = cart

    private val nextActivityResultLauncher: ActivityResultLauncher<Intent> = nextActivityResultLauncher

    /*
        Things that we need this to do:
            - on Click of X we remove te item from cart
            - modification of the quantity of an item
            - clicking confirm payment goes to the Payment activity
     */

    override fun getItemCount(): Int {
        return this.cart.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemBinding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindData(this.cart[position])
    }

    //add logic for the removal of items
}