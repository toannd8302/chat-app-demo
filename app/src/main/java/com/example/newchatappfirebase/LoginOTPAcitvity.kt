package com.example.newchatappfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newchatappfirebase.databinding.ActivityLoginOtpacitvityBinding
import com.example.newchatappfirebase.databinding.ActivityLoginPhoneNumberBinding

class LoginOTPAcitvity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOtpacitvityBinding
    var phoneNumber: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginOtpacitvityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        phoneNumber = intent.getStringExtra("phoneNumber")
        Toast.makeText(this, phoneNumber, Toast.LENGTH_LONG).show()
    }
}