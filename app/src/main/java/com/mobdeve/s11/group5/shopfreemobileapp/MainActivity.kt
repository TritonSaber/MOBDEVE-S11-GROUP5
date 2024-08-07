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
    companion object {
        lateinit var PACKAGE_NAME: String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //get package name
        PACKAGE_NAME = applicationContext.packageName

        this.titleBinding = TitlePageBinding.inflate(layoutInflater)
        setContentView(titleBinding.root)

        auth = Firebase.auth


        Log.d("[MAIN]", "Current View: $currentView ")
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
                moveToViewFoodActivity()
            }

            homepageBinding.ibMarkets.setOnClickListener{
                moveToMarketActivity()
            }

            homepageBinding.ibTransaction.setOnClickListener{

            }

            homepageBinding.ibTrackOrder.setOnClickListener {

            }

            homepageBinding.ibProfilePicture.setOnClickListener {
                Log.d("[MAIN]", "Profile Clicked")
                val intent = Intent(
                    this@MainActivity,
                    ProfileActivity::class.java
                )
                startActivity(intent)
            }
            homepageBinding.hpHome.setOnClickListener {
                //do nothing
                Toast.makeText(this, "You're already at the home page!", Toast.LENGTH_SHORT).show()
            }
            homepageBinding.hpProfile.setOnClickListener {
                Log.d("[MAIN]", "Profile Clicked")
                val intent = Intent(
                    this@MainActivity,
                    ProfileActivity::class.java
                )
                startActivity(intent)
            }
            homepageBinding.hpCart.setOnClickListener {
                Log.d("[MAIN]", "Cart Clicked")
                val intent = Intent(
                    this@MainActivity,
                    CartActivity::class.java
                )
                startActivity(intent)
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

    override fun onResume() {
        super.onResume()
        homepageBinding.ibViewFood.setOnClickListener{
            moveToViewFoodActivity()
        }

        homepageBinding.ibMarkets.setOnClickListener{
            moveToMarketActivity()
        }

        homepageBinding.ibTransaction.setOnClickListener{

        }

        homepageBinding.ibTrackOrder.setOnClickListener {

        }

        homepageBinding.ibProfilePicture.setOnClickListener {
            Log.d("[MAIN]", "Profile Clicked")
            val intent = Intent(
                this@MainActivity,
                ProfileActivity::class.java
            )
            startActivity(intent)
        }
        homepageBinding.hpHome.setOnClickListener {
            //do nothing
            Toast.makeText(this, "You're already at the home page!", Toast.LENGTH_SHORT).show()
        }
        homepageBinding.hpProfile.setOnClickListener {
            Log.d("[MAIN]", "Profile Clicked")
            val intent = Intent(
                this@MainActivity,
                ProfileActivity::class.java
            )
            startActivity(intent)
        }
        homepageBinding.hpCart.setOnClickListener {
            Log.d("[MAIN]", "Cart Clicked")
            val intent = Intent(
                this@MainActivity,
                CartActivity::class.java
            )
            startActivity(intent)
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
        Log.d("[MAIN]", "View Food Clicked")
        val intent = Intent(
            this@MainActivity,
            CategoryActivity::class.java
        )
        startActivity(intent)
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