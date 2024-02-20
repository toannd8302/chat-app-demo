package com.example.newchatappfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.example.newchatappfirebase.databinding.ActivityLoginPhoneNumberBinding

class LoginPhoneNumber : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPhoneNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPhoneNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var countryCodePicker = binding.loginCountryCode
        var phoneInput = binding.loginMobileNumber
        var btnSendOTP = binding.btSendOTP
        var progressBar = binding.loginProgressBar
        progressBar.visibility = View.GONE
        countryCodePicker.registerCarrierNumberEditText(
            phoneInput
        )
        btnSendOTP.setOnClickListener {
            if (!countryCodePicker.isValidFullNumber) {
                phoneInput.error = "Valid number is required"
                phoneInput.requestFocus()
                return@setOnClickListener
            }
            var intent = Intent(this, LoginOTPAcitvity::class.java)
            intent.putExtra("phoneNumber", countryCodePicker.fullNumberWithPlus)
            startActivity(intent)
        }


    }
}