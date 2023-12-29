package com.example.chat.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chat.databinding.ActivitySignUpBinding
import com.example.chat.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        binding.btnSignupSignUp.setOnClickListener {

            if (binding.etUserNameSignUp.text.isNotEmpty() && binding.etEmailSignUp.text.isNotEmpty()&& binding.etPasswordSignUp.text.isNotEmpty()){
                signUp(binding.etUserNameSignUp.text.toString(),
                    binding.etEmailSignUp.text.toString(),
                    binding.etPasswordSignUp.text.toString())
            }else{
                Toast.makeText(this, "Please write the email", Toast.LENGTH_SHORT).show()
            }

        }
        binding.doYouHaveAccount.setOnClickListener {
            val intentToLoginActivity = Intent(this@SignUpActivity,LoginActivity::class.java)
            startActivity(intentToLoginActivity)
        }

    }

    private fun signUp(name: String,email: String, password: String) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUpActivity, "Error Occurred", Toast.LENGTH_SHORT).show()
                }
            }

    }
    private fun addUserToDatabase(name: String,email: String,uid:String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name,email,uid))

    }
}