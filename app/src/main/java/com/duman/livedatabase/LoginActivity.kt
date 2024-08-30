package com.duman.livedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.duman.livedatabase.databinding.ActivityLoginBinding
import com.duman.livedatabase.databinding.ActivityMainBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.btnSSigned.setOnClickListener{
            btLoginEvent()
        }

        binding.tvRedirectLogin.setOnClickListener{
            signupInstead()
        }
    }

    private fun signupInstead() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToControl() {
        val intent = Intent(this,ControlActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun btLoginEvent() {
        val email = binding.etSEmailAddress.text.toString()
        val pass = binding.etSPassword.text.toString()

        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this){
            if(it.isSuccessful){
                goToControl()
            }else{
                Toast.makeText(this,"Login Gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}