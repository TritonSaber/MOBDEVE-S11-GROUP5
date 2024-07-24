package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.SignupBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TitlePageBinding
import com.squareup.picasso.Picasso
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.DevAccessBinding


class MainActivity : AppCompatActivity() {
    //Requisites
    private lateinit var binding: TitlePageBinding
    private lateinit var recyclerView: RecyclerView
    private var isAuthenticated: Boolean = false
    private var currentView: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.homepage)

        Picasso.get().load(R.drawable.sanmiglight)

        if(currentView == "title"){
            this.binding.hpLoginbtn.setOnClickListener(View.OnClickListener {
                moveToLoginActivity()
            })

            this.binding.hpSignupbtn.setOnClickListener(View.OnClickListener {
                moveToRegisterActivity()
            })
        }
        else if(currentView == "home"){

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

        // need if else for checking authentication
        if(isAuthenticated) {
            setContentView(R.layout.title_page)
            currentView = "title"
        }
        else {
            setContentView(R.layout.homepage)
            currentView = "home"
        }
    }


}