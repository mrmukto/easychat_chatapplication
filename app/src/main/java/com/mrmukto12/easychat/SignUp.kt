package com.mrmukto12.easychat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private  lateinit var editName: EditText
    private  lateinit var editEmail: EditText
    private  lateinit var editPassword: EditText
    private  lateinit var btnSignup: Button
    private  lateinit var mAuth : FirebaseAuth
    private  lateinit var mDbRef :DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.reg_email)
        editName = findViewById(R.id.reg_name)

        editPassword = findViewById(R.id.reg_password)
        btnSignup = findViewById(R.id.regsign_btn)

        btnSignup.setOnClickListener {
            val name = editName.text.toString()
            val email = editEmail.text.toString()
            val password = editPassword.text.toString()

            signUp(name,email, password);
        }


    }

    private fun signUp(name : String, email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email,mAuth.currentUser?.uid!!)
                    val  intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp, "Some Error", Toast.LENGTH_LONG ).show()


                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String) {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }


}