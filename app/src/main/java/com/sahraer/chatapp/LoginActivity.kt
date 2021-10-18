package com.sahraer.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sahraer.chatapp.databinding.ActivityLoginBinding
import com.sahraer.chatapp.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityLoginBinding.inflate(layoutInflater)
         val view = binding.root
         setContentView(view)
         binding.buttonLogin.setOnClickListener {
          performLogin()
        }

        binding.backToRegister.setOnClickListener {
            finish()
        }










    }

    private fun performLogin(){
        val email = binding.emailEdittextLogin.text.toString()
        val password = binding.passwordEdittextLogin.text.toString()

        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please enter text in email/password ",Toast.LENGTH_SHORT).show()
            return
        }

        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (!task.isSuccessful) return@addOnCompleteListener
                Log.d("LoginActivity", "Successfuly login user with uid: ${task.result?.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("LoginActivity","Failed to login user: ${it.message}")
            }
    }
}