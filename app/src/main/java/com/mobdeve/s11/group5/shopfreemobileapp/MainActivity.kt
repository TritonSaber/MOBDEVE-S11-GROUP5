package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.DevAccessBinding

class MainActivity : ComponentActivity() {
    //Requisites
    private lateinit var view: DevAccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        var viewBinding = DevAccessBinding.inflate(layoutInflater)
        view = viewBinding
        setContentView(view.root)
        Log.d("[DEV]", "Dev hub accessed.")

        view.daCart.setOnClickListener {
            Log.d("[DEV]", "Going to Cart Activity")
            val intent = Intent(
                this@MainActivity,
                CartActivity::class.java
            )
            startActivity(intent)
        }
    }
}