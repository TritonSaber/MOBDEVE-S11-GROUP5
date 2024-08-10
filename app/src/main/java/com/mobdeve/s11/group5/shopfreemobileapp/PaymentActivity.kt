package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.PaymentSelectBinding

class PaymentActivity: ComponentActivity() {
    private lateinit var paymentSelectBinding: PaymentSelectBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.paymentSelectBinding = PaymentSelectBinding.inflate(layoutInflater)
        setContentView(paymentSelectBinding.root)

        val spinner : Spinner = paymentSelectBinding.psPayments
        var receivedIntent = intent
        var totalPrice: Double = receivedIntent.getDoubleExtra(IntentKey.TOTAL_KEY, 0.00)

        ArrayAdapter.createFromResource(
            this,
            R.array.PaymentOption,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val intent = when (position) {
                        0 -> return
                        1 -> Intent(this@PaymentActivity, PaymentCreditActivity::class.java)
                        2 -> Intent(this@PaymentActivity, PaymentGCashActivity::class.java)
                        else -> return
                    }

                    intent.putExtra(IntentKey.TOTAL_KEY, totalPrice)

                    startActivity(intent)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do nothing
                }
            }

        this.paymentSelectBinding.psBack.setOnClickListener {
            val intent = Intent(this@PaymentActivity, CartActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}

