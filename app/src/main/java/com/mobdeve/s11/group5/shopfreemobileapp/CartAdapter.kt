package com.mobdeve.s11.group5.shopfreemobileapp

import android.app.Activity
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
            - clicking confirm payment goes to the Payment activity
     */

    override fun getItemCount(): Int {
        return this.cart.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemBinding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val cartViewHolder = CartViewHolder(itemBinding)

        //delete logic
        //val db = Firebase.firestore


        itemBinding.ciClose.setOnClickListener {view ->
            //remove it in the online database first

            //remove the item at the position
            (view.context as Activity).runOnUiThread {
                cart.removeAt(cartViewHolder.bindingAdapterPosition)
                notifyItemRemoved(cartViewHolder.bindingAdapterPosition)
            }
        }

        itemBinding.ciAddBtn.setOnClickListener {
            var viable = itemBinding.ciQuantity.text.toString().toIntOrNull()

            if (viable != null) {
                //an int
                var added = itemBinding.ciQuantity.text.toString().toInt() + 1
                itemBinding.ciQuantity.setText(added.toString())
            } else {
                itemBinding.ciQuantity.setText("0")
            }
        }


        itemBinding.ciRemoveBtn.setOnClickListener {
            //check if numerical
            var viable = itemBinding.ciQuantity.text.toString().toIntOrNull()

            if (viable != null) {
                //an int
                var subtracted = itemBinding.ciQuantity.text.toString().toInt() - 1
                itemBinding.ciQuantity.setText(subtracted.toString())
            } else {
                itemBinding.ciQuantity.setText("0")
            }
        }

        return cartViewHolder
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bindData(this.cart[position])
    }
}