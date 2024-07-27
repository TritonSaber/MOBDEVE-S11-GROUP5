package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.ProfilePageBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ProfileActivity : ComponentActivity () {
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : ProfilePageBinding
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var transactionlist: ArrayList<Transaction>
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewBinding = ProfilePageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //empty for testing
        transactionlist = ArrayList<Transaction>()

        this.recyclerView = viewBinding.ppTransactionHistory
        this.recyclerView.layoutManager = LinearLayoutManager(this@ProfileActivity)

        executorService.execute {
            var userid: String = ""
            dbRef = Firebase.firestore
            auth = Firebase.auth
            if (auth.currentUser != null) {
                userid = auth.currentUser!!.email.toString()

                Log.d("[PROFILE]", "Userid: $userid")

                dbRef.collection(MyFirestoreReferences.USERS_COLLECTION)
                    .whereEqualTo("email", userid)
                    .get().addOnSuccessListener { document ->
                        for (user in document) {
                            Log.d("[PROFILE]","${user.id} => ${user.data}")
                            var firstname = user.data["firstName"].toString()
                            var lastname = user.data["lastName"].toString()
                            var username = "$firstname $lastname"
                            viewBinding.ppUsername.text = username
                            viewBinding.ppEmail.text = user.data["email"].toString()
                        }
                    }

                dbRef.collection(MyFirestoreReferences.TRANSACTION_COLLECTION).whereEqualTo("tuserid", userid).get().addOnSuccessListener { documentSnapshots ->
                    for ( document in documentSnapshots) {

                        var productlist: ArrayList<Product> = ArrayList<Product>()

                        transactionlist.add(
                            Transaction(
                                null,
                                document.data["tdate"].toString(),
                                null,
                                document.data["tlocname"].toString(),
                                document.data["ttotal"].toString().toDouble(),
                                productlist, //placeholder
                                document.data["tcompleted"].toString().toBoolean()
                            )
                        )
                    }
                }
            }
            runOnUiThread {
                this.transactionAdapter = TransactionAdapter(transactionlist)
                this.recyclerView.adapter = transactionAdapter
            }
        }

        viewBinding.ppEdit.setOnClickListener {
            //not expecting any callbacks
            val intent = Intent(this@ProfileActivity, SettingsActivity::class.java)
            intent.putExtra(IntentKey.EMAIL_KEY, auth.currentUser?.email)
            startActivity(intent)
        }
    }

}