package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.HomePageBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.SignupBinding

class MainActivity : ComponentActivity() {
    //Requisites
    private lateinit var binding: HomePageBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.dev_access)
    }
}