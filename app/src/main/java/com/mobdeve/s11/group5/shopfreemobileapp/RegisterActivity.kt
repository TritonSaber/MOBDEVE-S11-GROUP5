package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.EMAIL_FIELD
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.FIRST_NAME_FIELD
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.LAST_NAME_FIELD
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.USERS_COLLECTION
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.SignupBinding

class RegisterActivity: ComponentActivity() {
    companion object {
        private const val TAG = "RegisterActivity"
    }

    private lateinit var dbRef: FirebaseFirestore
    private lateinit var signupBinding: SignupBinding
    private lateinit var auth: FirebaseAuth

    private fun confirmValid(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(
                baseContext,
                "Missing information. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun checkPassword(
        confirmation: Boolean,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (confirmation && (password.equals(confirmPassword))) {
            return true
        } else {
            Toast.makeText(
                baseContext,
                "Passwords do not match. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
    }

    private fun redirectMain() {
        val i = Intent(this@RegisterActivity, MainActivity::class.java)

        startActivity(i)
        finish()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.signupBinding = SignupBinding.inflate(layoutInflater)
        setContentView(signupBinding.root)

        signupBinding.suBack.setOnClickListener {
            //back button logic
            finish()
        }

        this.signupBinding.signup.setOnClickListener(View.OnClickListener {
            auth = Firebase.auth

            this.signupBinding.suConfirm.setOnClickListener(View.OnClickListener {

                val firstName = this.signupBinding.suFN.text.toString()
                val lastName = this.signupBinding.suLN.text.toString()
                val email = this.signupBinding.suEm.text.toString()
                val password = this.signupBinding.suPass.text.toString()
                val confirmPassword = this.signupBinding.suCPass.text.toString()

                val confirmation =
                    confirmValid(firstName, lastName, email, password, confirmPassword)
                val checkPass = checkPassword(confirmation, password, confirmPassword)

                if (confirmation && checkPass) {
                    dbRef = Firebase.firestore

                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "createUserWithEmail:success")
                                val user = auth.currentUser
                                val newAccount = hashMapOf(
                                    FIRST_NAME_FIELD to firstName,
                                    LAST_NAME_FIELD to lastName,
                                    EMAIL_FIELD to email
                                )

                                dbRef.collection(USERS_COLLECTION)
                                    .add(newAccount)
                                    .addOnSuccessListener { documentReference ->
                                        Log.d(
                                            TAG,
                                            "DocumentSnapshot added with ID: ${documentReference.id}"
                                        )
                                        Toast.makeText(
                                            baseContext,
                                            "Welcome to Shopfree!.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        redirectMain()
                                    }
                                    .addOnFailureListener { e ->
                                        Log.w(TAG, "Error adding document", e)
                                    }


                            } else {
                                Log.w(
                                    ContentValues.TAG,
                                    "createUserWithEmail:failure",
                                    task.exception
                                )
                                Toast.makeText(
                                    baseContext,
                                    "Failed to register user. Please try again.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                }
            })

            // Go back to title page
            this.signupBinding.suBack.setOnClickListener {
                finish()
            }
        })
    }
}