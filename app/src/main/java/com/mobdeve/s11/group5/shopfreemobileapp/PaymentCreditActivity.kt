package com.mobdeve.s11.group5.shopfreemobileapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.ComponentActivity
import com.mobdeve.s11.group5.shopfreemobileapp.databinding.PaymentCreditcardBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PaymentCreditActivity : ComponentActivity() {
    private lateinit var paymentCreditCardBinding: PaymentCreditcardBinding
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.paymentCreditCardBinding = PaymentCreditcardBinding.inflate(layoutInflater)
        setContentView(paymentCreditCardBinding.root)

        val spinner : Spinner = paymentCreditCardBinding.psPayments
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
                        2 -> Intent(this@PaymentCreditActivity, PaymentGCashActivity::class.java)
                        else -> return
                    }
                    startActivity(intent)
                }
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do nothing
                }

            }

        this.paymentCreditCardBinding.pccBack.setOnClickListener{
            val intent = Intent(this@PaymentCreditActivity, CartActivity::class.java)
            startActivity(intent)

            finish()
        }

        this.paymentCreditCardBinding.btnPay.setOnClickListener {
            executorService.execute{
                val intent = Intent(this@PaymentCreditActivity, PaymentCompleteActivity::class.java)

                startActivity(intent)
                finish()
            }
        }
    }
}