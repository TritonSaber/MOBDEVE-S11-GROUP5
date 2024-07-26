package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.HomepageBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TitlePageBinding


class MainActivity : ComponentActivity() {
    //Requisites
//    private lateinit var view: DevAccessBinding
    private lateinit var titleBinding: TitlePageBinding
    private lateinit var homepageBinding: HomepageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var auth: FirebaseAuth
    private var currentView: String = "title"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        this.titleBinding = TitlePageBinding.inflate(layoutInflater)
        setContentView(titleBinding.root)

        auth = Firebase.auth

//        FirebaseAuth.getInstance().signOut()

        Log.d("[MAIN]", "Current View: ${currentView.toString()}")
        if(currentView.equals("title")){
            Log.d("[MAIN]", "Buttons binded")
            titleBinding.hpLoginbtn.setOnClickListener {
                moveToLoginActivity()
            }

            titleBinding.hpSignupbtn.setOnClickListener{
                moveToRegisterActivity()
            }
        }
        else if(currentView.equals("home")){
            homepageBinding.ibViewFood.setOnClickListener{

            }

            homepageBinding.ibMarkets.setOnClickListener{
                moveToMarketActivity()
            }

            homepageBinding.ibTransaction.setOnClickListener{

            }

            homepageBinding.ibTrackOrder.setOnClickListener {

            }

            homepageBinding.ibProfilePicture.setOnClickListener {

            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        Log.d("[onStart]", "onStart initialized")
        // need if else for checking authentication
        if(currentUser != null) {
            this.homepageBinding = HomepageBinding.inflate(layoutInflater)
            setContentView(homepageBinding.root)
            currentView = "home"
        }
        else {
            setContentView(titleBinding.root)
            currentView = "title"
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

    private fun moveToViewFoodActivity(){
//        val i = Intent(this@MainActivity, ViewFoodActivity::class.java)
//        startActivity(i)
    }

    private fun moveToMarketActivity(){
        val i = Intent(this@MainActivity, MarketActivity::class.java)

        startActivity(i)
    }

    private fun moveToTransactionsActivity(){
//        val i = Intent(this@MainActivity, TransactionsActivity::class.java)
//
//        startActivity(i)
    }

    private fun moveToTrackOrderActivity(){
//        val i = Intent(this@MainActivity, TrackOrderActivity::class.java)
//
//        startActivity(i)
    }
}