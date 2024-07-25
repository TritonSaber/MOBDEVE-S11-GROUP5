package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.components.Component
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.ktx.Firebase
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.LoginBinding

class LoginActivity : ComponentActivity() {
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : LoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        this.viewBinding = LoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        auth = Firebase.auth

        this.viewBinding.loginConfirm.setOnClickListener(View.OnClickListener {
            val username = this.viewBinding.loginName.text.toString()
            val password = this.viewBinding.loginPass.text.toString()
            var userEmpty = false
            var passEmpty = false

            if(username.isEmpty()){
                this.viewBinding.loginName.setError("You forgot to enter your username.")
                userEmpty = true
            }

            if(password.isEmpty()){
                this.viewBinding.loginPass.setError("You forgot to enter your password.")
                passEmpty = true
            }

            if(userEmpty == false && passEmpty == false){
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            Log.d(TAG, "signInWithEmail:success")
                            val user = auth.currentUser
                            redirectMain()
                        }
                        else{
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Email and password do not match. Please try again.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }



        })

        // Go back to title page
        this.viewBinding.loginBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun redirectMain(){
        val i = Intent(this@LoginActivity, MainActivity::class.java)

        startActivity(i)
        finish()
    }

}