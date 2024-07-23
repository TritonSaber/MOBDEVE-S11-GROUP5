package com.mobdeve.s11.group5.shopfreemobileapp

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CartItemBinding
import com.squareup.picasso.Picasso

class CartViewHolder (private val itemBinding: CartItemBinding): RecyclerView.ViewHolder(itemBinding.root) {
    //data that will be binded to each line
    private lateinit var ciImage: ImageView
    private lateinit var ciName: TextView
    private lateinit var ciDesc: TextView
    //it's currently a Button but ImageButtons exist, will change later
    private lateinit var ciRemoveBtn: Button
    private lateinit var ciAddBtn: Button
    private lateinit var ciQuantity: EditText
    private lateinit var ciTotal: TextView

    init {
        this@CartViewHolder.ciAddBtn.setOnClickListener {
            //check if numerical
            var viable = this.ciQuantity.text.toString().toIntOrNull()

            if (viable != null) {
                //an int
                var subtracted = this.ciQuantity.text.toString().toInt() - 1
                this.ciQuantity.setText(subtracted.toString())
            } else {
                this.ciQuantity.setText("0")
            }
        }

        this@CartViewHolder.ciRemoveBtn.setOnClickListener {
            var viable = this.ciQuantity.text.toString().toIntOrNull()

            if (viable != null) {
                //an int
                var added = this.ciQuantity.text.toString().toInt() + 1
                this.ciQuantity.setText(added.toString())
            } else {
                this.ciQuantity.setText("0")
            }
        }
    }

    @SuppressLint("NotConstructor")
    fun CartViewHolder(itemBinding: CartItemBinding){
        this.ciImage = itemBinding.ciImage
        this.ciName = itemBinding.ciName
        this.ciDesc = itemBinding.ciDesc
        this.ciRemoveBtn = itemBinding.ciRemoveBtn
        this.ciAddBtn = itemBinding.ciAddBtn
        this.ciQuantity = itemBinding.ciQuantity
        this.ciTotal = itemBinding.ciTotal
    }

    //using the Product class to put in the data needed
    fun bindData(p: Product) {
        ciName.setText(p.pName)
        Picasso.get().load(p.imageUri).into(this.ciImage)
        ciDesc.setText(p.pDesc)
        //this will probably be 0
        ciTotal.setText((p.pQuantity!!.toInt() * p.pLocPrice).toString())
    }




}