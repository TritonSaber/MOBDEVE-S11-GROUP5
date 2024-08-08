package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CategoryViewBinding

class CategoryAdapter(categoryList: ArrayList<Category>, activityContext : Context): RecyclerView.Adapter<CategoryViewHolder>() {
    private val categoryList: ArrayList<Category> = categoryList
    private val activitycontext: Context = activityContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        Log.d("[CategoryAdapter]","$categoryList")
        val itemBinding = CategoryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val categoryViewHolder = CategoryViewHolder(itemBinding)

        //logic to go into the next activity
        val intent = Intent(activitycontext, ProductActivity::class.java)

        categoryViewHolder.itemView.setOnClickListener {
            //based on the name of the thing we'll get the category of items related ot it
            intent.putExtra(IntentKey.CATEGORY_KEY, categoryList[categoryViewHolder.bindingAdapterPosition].cCategoryName)

            //no need for result launcher
            activitycontext.startActivity(intent)
        }

        return categoryViewHolder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindData(this.categoryList[position])
    }

    override fun getItemCount(): Int {
        return this.categoryList.size
    }
}