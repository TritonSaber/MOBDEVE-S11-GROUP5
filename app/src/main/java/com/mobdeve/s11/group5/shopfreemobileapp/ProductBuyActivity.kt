package com.mobdeve.s11.group5.shopfreemobileapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProductBuyBinding
import com.squareup.picasso.Picasso
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProductBuyActivity: ComponentActivity() {
    private lateinit var productbuyBinding: ProductBuyBinding
    private lateinit var productList: ArrayList<Product>
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private var collectionexist: Boolean? = null
    private val storage = Firebase.storage

    private lateinit var mySpinner: Spinner
    private lateinit var spinnerAdapter: ArrayAdapter<String>
    private lateinit var price: String
    private lateinit var selectedProduct: CartItem
    private lateinit var loclist: ArrayList<ProductBuy>
    private lateinit var pricelist: ArrayList<String>
    private lateinit var locationid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.productbuyBinding = ProductBuyBinding.inflate(layoutInflater)
        //start binding stuff
        //spinnerAdapter = ArrayAdapter(this, )
        setContentView(productbuyBinding.root)

        this.mySpinner = productbuyBinding.pbLocChoice
        var intent = intent

        dbRef = Firebase.firestore
        var storageRef = storage.reference
        var productname = intent.getStringExtra(IntentKey.PRODUCT_KEY)

        productbuyBinding.pbName.text = productname

        mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                // Get the selected item
                locationid = loclist[position].pLocId

                //update the thingy
                runOnUiThread {
                    productbuyBinding.pbPrice.text = loclist[position].pPrice.toString()
                    productbuyBinding.pbLoc.text = loclist[position].pLocId
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        productbuyBinding.addBtn.setOnClickListener {
            var change = productbuyBinding.itemQuanEt.text.toString().toInt() + 1
            productbuyBinding.itemQuanEt.setText(change.toString(), TextView.BufferType.EDITABLE)
        }

        productbuyBinding.removeBtn.setOnClickListener {
            var change = productbuyBinding.itemQuanEt.text.toString().toInt() - 1
            productbuyBinding.itemQuanEt.setText(change.toString(), TextView.BufferType.EDITABLE)
        }

        productbuyBinding.pbCard.setOnClickListener {
            //no need to go to cart
            executorService.execute {
                //put it to the cart
                auth = Firebase.auth

                var user = auth.currentUser?.uid

                //get their cart
                if (user != null) {
                    dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                        .whereEqualTo("tuserid", user).whereEqualTo("tcompleted", false)
                        .get().addOnSuccessListener { documents ->
                            Log.d("[TRANSACTION]", "Received data: ${documents.documents}")
                            if (documents.documents.size > 1) {
                                Log.d("[TRANSACTION]", "ERROR USER HAS TWO ACTIVE CARTS")
                            } else {
                                //the cart
                                Log.d("[TRANSACTION]", "User Cart: ${documents.documents.first().id} => ${documents.documents.first().data}")
                                var docref = documents.documents.first().id
                                var productid = String()

                                //find the specific item and pass the ID instead, less stuff to pass
                                dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION)
                                    .whereEqualTo("plocId", locationid)
                                    .whereEqualTo("pname", productname)
                                    .get().addOnSuccessListener { document ->
                                    productid = document.documents[0].id
                                }.addOnCompleteListener {
                                    selectedProduct = CartItem(productid, productbuyBinding.itemQuanEt.text.toString().toInt())
                                    dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                                        .document(docref)
                                        .update("cart", FieldValue.arrayUnion(selectedProduct))

                                        runOnUiThread {
                                            finish()
                                        }
                                }
                            }
                    }
                }
            }
        }

        executorService.execute {
            dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION)
                .whereEqualTo("pname", productname)
                .get().addOnSuccessListener { documents ->
                    var imageref = documents.documents[0].data?.get("pstorageURL")?.toString()
                        ?.let { storageRef.child(it) }
                    loclist = ArrayList()
                    pricelist = ArrayList()
                    var downloadedimage: Uri? = null

                    for (document in documents) {
                        loclist.add(
                            ProductBuy(
                                document.data["plocId"].toString(),
                                document.data["pprice"].toString().toDouble()
                            )
                        )
                        pricelist.add(
                            document.data["plocId"].toString()
                        )
                    }
                    spinnerAdapter = ArrayAdapter(this, R.layout.textvew_black, pricelist)

                    if (imageref != null) {
                        imageref.downloadUrl.addOnSuccessListener { image ->
                            downloadedimage = image
                        }.addOnCompleteListener {
                            runOnUiThread {
                                Picasso.get().load(downloadedimage).into(productbuyBinding.pbImage)
                                this.mySpinner.adapter = spinnerAdapter
                            }
                        }
                    }



            }.addOnCompleteListener {

            }
        }



    }
}