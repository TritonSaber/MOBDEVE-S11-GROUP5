package com.mobdeve.s11.group5.shopfreemobileapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CategoryViewBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.MarketItemBinding

class CategoryAdapter(categoryList: ArrayList<Category>): RecyclerView.Adapter<CategoryViewHolder>() {
    private val categoryList: ArrayList<Category> = categoryList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = CategoryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val categoryViewHolder = CategoryViewHolder(itemBinding)

        return categoryViewHolder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(this.categoryList[position])
    }

    override fun getItemCount(): Int {
        return this.categoryList.size
    }
}