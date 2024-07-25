package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.SignupBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TitlePageBinding
import com.squareup.picasso.Picasso
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.DevAccessBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.HomepageBinding


class MainActivity : ComponentActivity() {
    //Requisites
//    private lateinit var view: DevAccessBinding
    private lateinit var titleBinding: TitlePageBinding
    private lateinit var homepageBinding: HomepageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private var currentView: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        this.titleBinding = TitlePageBinding.inflate(layoutInflater)
        setContentView(R.layout.title_page)

        auth = Firebase.auth

//        Picasso.get().load(R.drawable.sanmiglight)

        if(currentView.equals("title")){
            val btnLogin = titleBinding.hpLoginbtn
            val btnSignUp = titleBinding.hpSignupbtn
            btnLogin.setOnClickListener(View.OnClickListener {
                Toast.makeText(
                    baseContext,
                    "Tests",
                    Toast.LENGTH_SHORT
                ).show()
                moveToLoginActivity()
            })

            btnSignUp.setOnClickListener(View.OnClickListener {
                moveToRegisterActivity()
            })
        }
        else if(currentView.equals("home")){

        }
    }

    private fun moveToLoginActivity(){
        val i = Intent(this@MainActivity, LoginActivity::class.java)

        startActivity(i)
    }

    private fun moveToRegisterActivity(){
        val i = Intent(this@MainActivity, RegisterActivity::class.java)

        startActivity(i)
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        // need if else for checking authentication
        if(currentUser != null) {
            this.homepageBinding = HomepageBinding.inflate(layoutInflater)
            setContentView(R.layout.homepage)
            currentView = "home"
        }
        else {
            setContentView(R.layout.title_page)
            currentView = "title"
        }
    }


}