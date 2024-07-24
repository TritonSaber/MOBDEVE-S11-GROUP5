package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.SignupBinding

class RegisterActivity: AppCompatActivity() {
    private lateinit var dbRef: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding : SignupBinding = SignupBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}