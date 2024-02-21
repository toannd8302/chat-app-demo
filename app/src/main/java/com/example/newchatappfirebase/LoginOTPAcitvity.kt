package com.example.newchatappfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.newchatappfirebase.databinding.ActivityLoginOtpacitvityBinding
import com.example.newchatappfirebase.databinding.ActivityLoginPhoneNumberBinding
import com.example.newchatappfirebase.utils.AndroidUtils

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseOptions

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.FileInputStream
import java.util.concurrent.TimeUnit

class LoginOTPAcitvity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginOtpacitvityBinding
    private var phoneNumber: String? = null
    private lateinit var progressBar: ProgressBar
    private lateinit var btNext: Button
    private lateinit var editTextCode: EditText
    private var auth = FirebaseAuth.getInstance()
    var verificationCode: String? = null
    var resendingToken: PhoneAuthProvider.ForceResendingToken? = null
    var builder: PhoneAuthOptions.Builder? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginOtpacitvityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        phoneNumber = intent.getStringExtra("phoneNumber")
        Log.d("LoginOTPAcitvity", "Phone number: $phoneNumber")
        progressBar = binding.loginProgressBar
        btNext = binding.btLoginNext
        editTextCode = binding.edtOTP
        sendOTP(phoneNumber!!, false)

    }


    private fun sendOTP(phoneNumber: String, isResend: Boolean) {
        setInProgress(true)
        //Phone Auth
        var builder =
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)       // Phone number to verify
                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                .setActivity(this)                 // Activity (for callback binding)
                .setCallbacks(
                    object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                            var code = p0.smsCode
                            //sometime the code is not detected automatically
                            //in this case the code will be null
                            //so user has to manually enter the code
                            if (code != null) {
                                editTextCode.setText(code);
                                //verifying the code
                                //verifyVerificationCode(code);
                            }
                            signIn(p0)
                            setInProgress(false)
                        }

                        override fun onVerificationFailed(p0: FirebaseException) {
                            AndroidUtils.showToast(applicationContext, "Verification failed")
                            setInProgress(false)
                        }

                        override fun onCodeSent(
                            p0: String,
                            p1: PhoneAuthProvider.ForceResendingToken
                        ) {
                            super.onCodeSent(p0, p1)
                            verificationCode = p0
                            resendingToken = p1
                            AndroidUtils.showToast(applicationContext, "Code sent")
                            setInProgress(false)
                        }

                    })
        if (isResend) {
            PhoneAuthProvider.verifyPhoneNumber(
                builder.setForceResendingToken(resendingToken!!).build()
            )
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build())
        }
    }

    private fun setInProgress(isInProgress: Boolean) {
        if (isInProgress) {
            progressBar.visibility = View.VISIBLE
            btNext.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            btNext.visibility = View.VISIBLE
        }
    }
}

private fun signIn(p0: PhoneAuthCredential) {
//
}