package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TitlePageBinding
import com.squareup.picasso.Picasso


class MainActivity : ComponentActivity() {
    //Requisites
    private lateinit var binding: TitlePageBinding
    private lateinit var recyclerView: RecyclerView
    private var isAuthenticated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.homepage)

        Picasso.get().load(R.drawable.sanmiglight)
    }

    override fun onStart() {
        super.onStart()

        // need if else for checking authentication
        if(isAuthenticated) {
            setContentView(R.layout.title_page)
        }
        else {
            setContentView(R.layout.homepage)
        }
    }
}