package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.PaymentGcashBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PaymentGCashActivity: ComponentActivity() {
    private lateinit var paymentGCashBinding: PaymentGcashBinding
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.paymentGCashBinding = PaymentGcashBinding.inflate(layoutInflater)
        setContentView(paymentGCashBinding.root)

        var receivedIntent = intent
        var totalPrice = receivedIntent.getDoubleExtra(IntentKey.TOTAL_KEY, 0.00)

        val spinner : Spinner = paymentGCashBinding.psPayments
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
                    1 -> Intent(this@PaymentGCashActivity, PaymentCreditActivity::class.java)
                    else -> return
                }
                intent.putExtra(IntentKey.TOTAL_KEY, totalPrice)
                startActivity(intent)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // do nothing
            }

        }

        this.paymentGCashBinding.psBack.setOnClickListener{
            val intent = Intent(this@PaymentGCashActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        this.paymentGCashBinding.btnPay.setOnClickListener {
            executorService.execute{
                val intent = Intent(this@PaymentGCashActivity, PaymentCompleteActivity::class.java)
                intent.putExtra(IntentKey.TOTAL_KEY, totalPrice)
                startActivity(intent)
                finish()
            }
        }
    }
}