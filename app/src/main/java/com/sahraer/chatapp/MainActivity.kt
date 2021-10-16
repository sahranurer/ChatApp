package com.sahraer.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    val username_edittext_register by lazy {findViewById<EditText>( R.id.username_edittext_register) }
    val email_edittext_register by lazy { findViewById<EditText>(R.id.email_edittext_register) }
    val password_edittext_register by lazy { findViewById<EditText>(R.id.password_edittext_register) }
    val register_button by lazy {findViewById<Button>(R.id.button_register)}
    val already_have_account_textView by lazy {findViewById<TextView>(R.id.already_have_account_textView)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        register_button.setOnClickListener {
            Log.d("MainActivity", "Email is : "+email_edittext_register.text.toString())
            Log.d("MainActivity","Password is : "+password_edittext_register.text.toString())
        }

        already_have_account_textView.setOnClickListener {
            Log.d("MainActivity","Try to show login activity ")
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
        }



    }
}