package com.duman.livedatabase


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.duman.livedatabase.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        binding.btnSSigned.setOnClickListener {
            signUpUser()
        }

        binding.tvRedirectLogin.setOnClickListener{
            loginInstead()
        }
    }

    private fun loginInstead() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun signUpUser() {
        val email = binding.etSEmailAddress.text.toString()
        val pass = binding.etSPassword.text.toString()
        val confirm = binding.etSConfPassword.text.toString()

        if (email.isBlank() || pass.isBlank() || confirm.isBlank()){
            Toast.makeText(this,"form tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show()
            return
        }

        if (pass != confirm){
            Toast.makeText(this,"password dan confirm tidak cocok", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this){
            if (it.isSuccessful){
                Toast.makeText(this,"Pendaftaran Berhasil", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this,"Pendaftaran Gagal dilakukan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}