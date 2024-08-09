package com.mobdeve.s11.group5.shopfreemobileapp

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.CartItemBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CartAdapter (cart: ArrayList<CartItem>, nextActivityResultLauncher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<CartViewHolder>() {
    private val cart: ArrayList<CartItem> = cart
    private lateinit var products: ArrayList<Product>
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private var collectionexist: Boolean? = null
    private val storage = Firebase.storage


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

        //get the actual cart
        dbRef = Firebase.firestore
        var storageRef = storage.reference
        var currentuser = auth.currentUser?.uid

        executorService.execute {
            for (item in cart) {
                item.productUID?.let {
                    dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION).document(it).get().addOnSuccessListener { document ->
                        //get the image
                        var imageref = storageRef.child(document.data!!["pstorageURL"].toString())

                        imageref.downloadUrl.addOnSuccessListener { image ->
                            products.add(
                                Product(
                                    document.data!!["pname"].toString(),
                                    document.data!!["plocId"].toString(),
                                    document.data!!["pprice"].toString().toDouble(),
                                    document.data!!["pstorageURL"].toString(),
                                    image,
                                    item.quantity,
                                    document.data!!["pdesc"].toString(),
                                    document.data!!["pcategory"].toString(),
                                    document.data!!["pperWeight"].toString()
                                )
                            )
                        }
                    }.addOnFailureListener { task ->
                        Log.d("[CART-Adapter]", "Failed: ${task.stackTrace}")
                    }
                }
            }
        }

        //delete logic
        //val db = Firebase.firestore


        itemBinding.ciClose.setOnClickListener {view ->
            //remove it in the online database first

            dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION).whereEqualTo("userid", currentuser).whereEqualTo("tcompleted", false).get().addOnSuccessListener { document ->
                if (document.documents.size > 1) {
                    //database error wahoo
                } else {
                    //remove the specific item from the cart
                    var docref = document.documents[0].id
                    dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION).document(docref).update(
                        hashMapOf<String, Any>(
                            "cart.$docref" to FieldValue.delete()
                        )
                    )
                }
            }

            //remove the item at the position in the UI
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
        holder.bindData(this.products[position])
    }
}