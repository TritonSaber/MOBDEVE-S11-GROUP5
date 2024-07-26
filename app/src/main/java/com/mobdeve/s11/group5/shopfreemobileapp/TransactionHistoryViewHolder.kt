package com.mobdeve.s11.group5.shopfreemobileapp

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TransactionItemBinding
import com.squareup.picasso.Picasso

class TransactionHistoryViewHolder (private val itemBinding: TransactionItemBinding): RecyclerView.ViewHolder(itemBinding.root){
    private var tiImage: ImageView = itemBinding.tiImage
    private var tiName: TextView = itemBinding.tiName
    private var tiDesc: TextView = itemBinding.tiDesc
    private var tiQuantity: TextView = itemBinding.tiQuantity
    private var tiTotal: TextView = itemBinding.tiTotal

    fun bindData(p: Product) {
        tiName.text = p.pName
        Picasso.get().load(p.imageUri).into(this.tiImage)
        tiDesc.text = p.pDesc
        //this will probably be 0
        tiQuantity.text = p.pQuantity.toString()
        tiTotal.text = ((p.pQuantity!!.toInt() * p.pPrice).toString())
    }

}