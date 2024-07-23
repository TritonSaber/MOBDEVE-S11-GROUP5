package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.LoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var dbRef: FirebaseFirestore
    private lateinit var viewBinding : LoginBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        this.viewBinding = LoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.viewBinding.loginConfirm.setOnClickListener(View.OnClickListener {
            val username = this.viewBinding.loginName.text.toString()
            val password = this.viewBinding.loginPass.text.toString()



        })

        /*val query = usersRef.whereEqualTo)
        *   MyFirestoreReferences.USERNAME_FIELD,
        *   username
        * )*/
    }
}