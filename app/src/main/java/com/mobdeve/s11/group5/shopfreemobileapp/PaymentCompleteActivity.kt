package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.PaymentCompleteBinding
import java.util.Calendar
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PaymentCompleteActivity : ComponentActivity() {
    private lateinit var paymentCompleteBinding: PaymentCompleteBinding
    private lateinit var dbRef: FirebaseFirestore
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val storage = Firebase.storage
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var cart: ArrayList<CartItem> = ArrayList()
        cart.add(CartItem(null, null))

        dbRef = Firebase.firestore
        auth = Firebase.auth

        var receivedIntent = intent
        var totalPrice = receivedIntent.getDoubleExtra(IntentKey.TOTAL_KEY, 0.00)

        this.paymentCompleteBinding = PaymentCompleteBinding.inflate(layoutInflater)
        setContentView(paymentCompleteBinding.root)

        this.paymentCompleteBinding.pcpButton.setOnClickListener {
            val intent = Intent(this@PaymentCompleteActivity, MainActivity::class.java)

            executorService.execute{
            dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                .whereEqualTo("tuserid", auth.currentUser?.uid)
                .whereEqualTo("tcompleted", false)
                .get().addOnSuccessListener { document ->
                    var docref = document.documents.first().id
                    dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                        .document(docref)
                        .update("tcompleted", true)
                        .addOnCompleteListener{
                            dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION)
                                .document(docref)
                                .update("ttotal", totalPrice)
                            var usercart = Transaction(
                                auth.currentUser?.uid,
                                Calendar.getInstance().time.toString(),
                                0.00,
                                cart,
                                false
                            )
                            dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION).document().set(usercart).addOnSuccessListener {
                                Log.d("[TRANSACTION]", "User cart generated")
                            }.addOnFailureListener { task ->
                                Log.d("[TRANSACTION]", "Failed to create: ${task.stackTrace} ")
                            }.addOnCompleteListener{
                                startActivity(intent)
                                finish()
                            }
                        }
                    }
                }
        }
    }
}