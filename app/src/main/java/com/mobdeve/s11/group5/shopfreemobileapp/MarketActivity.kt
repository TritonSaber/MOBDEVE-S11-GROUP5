package com.mobdeve.s11.group5.shopfreemobileapp

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.MarketsBinding

class MarketActivity: ComponentActivity() {
    private lateinit var marketsBinding: MarketsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.marketsBinding = MarketsBinding.inflate(layoutInflater)
        setContentView(marketsBinding.root)
    }
}