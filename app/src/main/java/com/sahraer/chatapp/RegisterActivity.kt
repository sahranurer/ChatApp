package com.sahraer.chatapp


import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sahraer.chatapp.databinding.ActivityMainBinding


class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)




        binding.buttonRegister.setOnClickListener {
            performRegister()
        }


        binding.alreadyHaveAccountTextView.setOnClickListener {
            Log.d("RegisterActivity", "Try to show login activity ")
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSelectImage.setOnClickListener {
            Log.d("MainActivity","Try to photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,0)


        }





    }






    private fun performRegister(){
        val email = binding.emailEdittextRegister.text.toString()
        val password = binding.passwordEdittextRegister.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this,"Please enter text in email/password ",Toast.LENGTH_SHORT).show()
            return
        }


        // Initialize Firebase Auth
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (!task.isSuccessful) return@addOnCompleteListener

            // If sign in fails, display a message to the user.
            Log.d("MainActivity", "Successfuly created user with uid: ${task.result?.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("MainActivity","Failed to create user: ${it.message}")
                Toast.makeText(this,"Failed to create user: ${it.message}",Toast.LENGTH_SHORT).show()
            }

    }




}

