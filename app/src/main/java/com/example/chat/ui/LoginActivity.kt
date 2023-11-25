package com.example.chat.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chat.R
import com.example.chat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()

        binding.btnSignupLogin.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
        binding.btnLoginLogin.setOnClickListener {
            if(binding.etEmailLogin.text.isNotEmpty() && binding.etPasswordLogin.text.isNotEmpty()){
                login(
                    binding.etEmailLogin.text.toString(),
                    binding.etPasswordLogin.text.toString()
                )
            }else{
                Toast.makeText(this, "Please write a email", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun login(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        finish()
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "User does not exist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this@LoginActivity, "Please Write a email", Toast.LENGTH_SHORT).show()
        }


    }
}