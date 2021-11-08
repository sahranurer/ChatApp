package com.sahraer.chatapp


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.sahraer.chatapp.databinding.ActivityMainBinding
import java.util.*


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

    var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==0 && resultCode == Activity.RESULT_OK && data!=null){
            //proceed and check what the selected image was...
            Log.d("RegisterActivity","Photo was selected")

            selectedPhotoUri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver,selectedPhotoUri)

            val bitmapDrawable= BitmapDrawable(bitmap)
            binding.buttonSelectImage.setBackgroundDrawable(bitmapDrawable)

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
            uploadImageToFirebaseStorage()
            }
            .addOnFailureListener {
                Log.d("MainActivity","Failed to create user: ${it.message}")
                Toast.makeText(this,"Failed to create user: ${it.message}",Toast.LENGTH_SHORT).show()
            }

    }

    private fun uploadImageToFirebaseStorage(){
        if (selectedPhotoUri == null) return
        val filename = UUID.randomUUID().toString()
        val ref =   FirebaseStorage.getInstance().getReference("/images/$filename")
        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d("Register","Successfly upload image : ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                   Log.d("RegisterActivity","File Location : $it")
                    saveUserToFireBaseDatabase()
                }
        }
    }

    private fun  saveUserToFireBaseDatabase(){
       val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

    }



}

