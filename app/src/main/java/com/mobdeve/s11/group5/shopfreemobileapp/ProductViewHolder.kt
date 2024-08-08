package com.mobdeve.s11.group5.shopfreemobileapp

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProductItemBinding
import com.squareup.picasso.Picasso

class ProductViewHolder(private val itemBinding: ProductItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
    private var piImage: ImageView = itemBinding.piImage
    private var piName: TextView = itemBinding.piName
    private var piDesc: TextView = itemBinding.piDesc

    fun bindData(p : Product) {
        piName.text = p.pName
        //this'll be Price in this case
        if (p.pPerWeight != null) {
            piDesc.text = "₱" + p.pPrice + " " + p.pPerWeight //this'll work if it's per weight or nots
        } else {
            piDesc.text = "₱" + p.pPrice
        }

        Picasso.get().load(p.pImageUri).into(this.piImage) //load the downloaded image
    }
}