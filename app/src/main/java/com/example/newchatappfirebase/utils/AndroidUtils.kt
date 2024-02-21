package com.example.newchatappfirebase.utils

import android.content.Context
import android.widget.Toast

class AndroidUtils {
    companion object{
        @JvmStatic
        fun showToast(context: Context , message: String){
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

        }
    }
}