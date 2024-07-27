package com.mobdeve.s11.group5.shopfreemobileapp

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CategoryViewBinding
import com.squareup.picasso.Picasso

class CategoryViewHolder(private val itemBinding: CategoryViewBinding): RecyclerView.ViewHolder(itemBinding.root) {
    private var pcImage: ImageView = itemBinding.pcImage
    private var pcName: TextView = itemBinding.pcName

    fun bindData(c: Category){
        Log.d("[CATEGORY]", "Binding: $c")
        pcName.text = c.cCategoryName
        Picasso.get().load(c.cImage)
    }
}