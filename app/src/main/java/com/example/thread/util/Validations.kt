package com.example.thread.util

import android.util.Patterns
import java.util.regex.Pattern

class Validations {
    companion object {
        fun isValidEmail(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }
    }
}
