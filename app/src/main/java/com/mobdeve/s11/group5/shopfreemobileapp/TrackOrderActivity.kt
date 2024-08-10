package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.TrackorderBinding

class TrackOrderActivity: ComponentActivity() {
    private lateinit var viewBinding : TrackorderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.viewBinding = TrackorderBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


    }
}