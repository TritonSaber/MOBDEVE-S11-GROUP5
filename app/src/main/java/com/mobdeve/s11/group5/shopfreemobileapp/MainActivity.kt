package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.SignupBinding
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TitlePageBinding

class MainActivity : ComponentActivity() {
    //Requisites
    private lateinit var binding: TitlePageBinding
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.dev_access)
    }
}