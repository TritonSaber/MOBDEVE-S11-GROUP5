package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.EMAIL_FIELD
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.FIRST_NAME_FIELD
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.LAST_NAME_FIELD
import com.mobdeve.s11.group5.shopfreemobileapp.MyFirestoreReferences.USERS_COLLECTION
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.SignupBinding

class RegisterActivity: ComponentActivity() {
    companion object{
        private const val TAG = "RegisterActivity"
    }
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : SignupBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        this.viewBinding = SignupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.suBack.setOnClickListener {
            //back button logic
            finish()
        }

        this.viewBinding.signup.setOnClickListener(View.OnClickListener {

            val firstName = this.viewBinding.suFN.text.toString()
            val lastName = this.viewBinding.suLN.text.toString()
            val email = this.viewBinding.suEm.text.toString()
            val password = this.viewBinding.suPass.text.toString()
            val confirmPassword = this.viewBinding.suConfirm.text.toString()

            val confirmation = confirmValid(firstName,lastName,email,password,confirmPassword)
            val checkPass = checkPassword(confirmation,password,confirmPassword)

            if(confirmation && checkPass) {
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
                                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                                }
                                .addOnFailureListener { e ->
                                    Log.w(TAG, "Error adding document", e)
                                }


                        }
                        else{
                            Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Failed to register user. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        })

    }

    private fun confirmValid(firstName:String, lastName:String, email:String, password:String, confirmPassword:String): Boolean{
        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Toast.makeText(
                baseContext,
                "Missing information. Please try again.",
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        else{
            return true
        }
    }

    private fun checkPassword(confirmation: Boolean, password:String, confirmPassword:String): Boolean{
        if(confirmation && (password.equals(confirmPassword))){
            return true
        }
        else{
            return false
        }
    }
}