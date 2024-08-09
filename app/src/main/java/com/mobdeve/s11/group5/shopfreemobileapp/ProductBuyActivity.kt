package com.mobdeve.s11.group5.shopfreemobileapp

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
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

        mySpinner.setOnItemClickListener { parent, view, position, id ->
            var locationid = loclist
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
                        .whereEqualTo("userid", user)
                        .whereEqualTo("tcompleted", false)
                        .get().addOnSuccessListener { documents ->
                            if (documents.documents.size > 1) {
                                Log.d("[TRANSACTION]", "ERROR USER HAS TWO ACTIVE CARTS")
                            } else {
                                var docref = documents.documents[0].id

                                //find the specific item and pass the ID instead, less stuff to pass

                                dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                                    .document(docref)
                                    .update("cart.$docref", FieldValue.arrayUnion(selectedProduct))
                            }
                    }
                }
            }
        }

        executorService.execute {
            dbRef.collection(MyFirestoreReferences.PRODUCT_COLLECTION)
                .whereEqualTo("pname", intent.getStringExtra(IntentKey.PRODUCT_KEY))
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
                            document.data["pprice"].toString()
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