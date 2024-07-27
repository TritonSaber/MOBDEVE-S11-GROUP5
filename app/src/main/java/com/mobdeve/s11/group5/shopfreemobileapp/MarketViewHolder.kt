package com.mobdeve.s11.group5.shopfreemobileapp

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.MarketItemBinding
import com.squareup.picasso.Picasso

class MarketViewHolder (private val itemBinding: MarketItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
    //data that will be binded to each line
    private var miImage: ImageView = itemBinding.miImage
    private var miName: TextView = itemBinding.miName
    private var miLoc: TextView = itemBinding.miLoc

    //using the Product class to put in the data needed
    fun bindData(m: Market) {
        Log.d("[MARKET]", "Binding: $m")
        miName.text = m.mName
        Picasso.get().load(m.mImage).into(this.miImage)
        miLoc.text = m.mLoc
    }
}