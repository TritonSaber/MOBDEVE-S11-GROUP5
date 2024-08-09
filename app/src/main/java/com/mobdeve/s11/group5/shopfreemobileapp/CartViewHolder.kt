package com.mobdeve.s11.group5.shopfreemobileapp

import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CartItemBinding
import com.squareup.picasso.Picasso

class CartViewHolder (private val itemBinding: CartItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
    //data that will be binded to each line
    private var ciImage: ImageView = itemBinding.ciImage
    private var ciName: TextView = itemBinding.ciName
    private var ciDesc: TextView = itemBinding.ciDesc
    //it's currently a Button but ImageButtons exist, will change later
    private var ciRemoveBtn: Button = itemBinding.ciRemoveBtn
    private var ciAddBtn: Button = itemBinding.ciAddBtn
    private var ciQuantity: EditText = itemBinding.ciQuantity
    private var ciTotal: TextView = itemBinding.ciTotal
    private var ciClose: ImageButton = itemBinding.ciClose

    /*
    public fun CartViewHolder(itemBinding: CartItemBinding){
        this.ciImage = itemBinding.ciImage
        this.ciName = itemBinding.ciName
        this.ciDesc = itemBinding.ciDesc
        this.ciRemoveBtn = itemBinding.ciRemoveBtn
        this.ciAddBtn = itemBinding.ciAddBtn
        this.ciQuantity = itemBinding.ciQuantity
        this.ciTotal = itemBinding.ciTotal
        this.ciClose = itemBinding.ciClose
    }*/

    //using the Product class to put in the data needed
    fun bindData(p: Product) {
        Log.d("[CART]", "Binding this piece of data: $p")
        ciName.text = p.pName
        Picasso.get().load(p.pImageUri).into(this.ciImage)
        ciDesc.text = p.pDesc
        if (p.pDesc == "null") {
            ciDesc.text = null
        } else {
            ciDesc.text = p.pDesc
        }
        //this will probably be 0
        ciTotal.text = ((p.pQuantity!!.toInt() * p.pPrice).toString())
        ciQuantity.setText(ciQuantity.text.toString(), TextView.BufferType.EDITABLE)
    }




}